package demo.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import demo.common.Payment;
import demo.common.TransactionRequest;
import demo.common.TransactionResponse;
import demo.entity.Order;
import demo.entity.Product;
import demo.repository.OrderRepository;
import demo.repository.ProductRepository;
import demo.response.OrderFindAllResponse;
import demo.util.OrderIdGenerator;

@Service
public class OrderService {
	
	private static Logger logger = LoggerFactory.getLogger(OrderService.class); 
	
	@Autowired
	private OrderRepository repository;
	
	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private RestTemplate template;
	
	@Autowired
	private OrderIdGenerator orderIdGenerator;

	public TransactionResponse saveOrder(TransactionRequest request)
	{
		Order order = request.getOrder();
		Product product = productRepository.findById(order.getProductId());
		order.setName(product.getProductName());
		order.setPrice(product.getPrice());
		order.setStoreId(product.getStore().getStoreId());
		order.setOrderDate(new Date());
		
		if(order.isGuest())
			order.setCustomerId(-1);
		
		String response = "";
		Payment payment = request.getPayment();
		payment.setOrderId(order.getId());
		payment.setAmount(order.getPrice()*order.getQty());

		Payment paymentResponse = template.postForObject("http://PAYMENT-SERVICE/payment/doPayment",payment,Payment.class);

		Optional<Payment> paymentOptional = Optional.ofNullable(paymentResponse);
		
		if(paymentOptional.isPresent()) {
			if(paymentOptional.get().getPaymentStatus().equals("success"))
				order.setStatus("PLACED");
			else
				order.setStatus("On-HOLD");
		}
		else
			logger.error("Got null response from payment service");

		repository.save(order);
		
		TransactionResponse transactionResponse = new TransactionResponse();
		transactionResponse.setOrder(order);
		if(paymentOptional.isPresent())
			transactionResponse.setTransactionId(paymentOptional.get().getTransactionId());
		else
			logger.error("Got null response from payment service");
		transactionResponse.setAmount(payment.getAmount());
		transactionResponse.setMessage(response);
		return transactionResponse;	
	}

	public Order findOrderByOrderId(int orderId) {
		return repository.findById(orderId);
	}

	public List<Order> findOrderByStoreId(int storeId) {

		if(storeId==0)
			storeId=1; //Setting default value for storeId as 1, if agent selects nothing on ui
		return repository.findByStoreId(storeId);
	}

	public List<Order> findOrderByCustomerId(int customerId){
		return repository.findByCustomerId(customerId);
	}

	public List<Order> findOrderByStatus(String status){
		if(status.equalsIgnoreCase("all"))
			return repository.findAll();
		return repository.findByStatus(status);
	}

	public OrderFindAllResponse getAllOrders(){
		List<Order> list = repository.findAll();

		OrderFindAllResponse response = new OrderFindAllResponse();

		if(list.isEmpty())
			response.setMessage("No result found!");
		else {
			response.setList(list);
			response.setMessage("Found "+list.size()+" orders");
		}

		return response;
	}

	public OrderFindAllResponse getAllOrderSortByStatus(String sort) {


		List<Order> list;

		if(sort.equalsIgnoreCase("asc"))
			list=repository.findAllByOrderByStatusAsc();
		else
			list= repository.findAllByOrderByStatusDesc();

		OrderFindAllResponse response = new OrderFindAllResponse();

		if(list.isEmpty())
			response.setMessage("No result found!");
		else {
			response.setList(list);
			response.setMessage("Found "+list.size()+" orders");
		}

		return response;

	}

	public OrderFindAllResponse getAllOrderSortByOrderDate(String sort) {


		List<Order> list;

		if(sort.equalsIgnoreCase("asc"))
			list=repository.findAllByOrderByOrderDateAsc();
		else
			list= repository.findAllByOrderByOrderDateDesc();

		OrderFindAllResponse response = new OrderFindAllResponse();

		if(list.isEmpty())
			response.setMessage("No result found!");
		else {
			response.setList(list);
			response.setMessage("Found "+list.size()+" orders");
		}

		return response;

	}
	
	public Page<Order> findAllPaginatedOrders(Pageable p){
		return repository.findAll(p);
	}
}

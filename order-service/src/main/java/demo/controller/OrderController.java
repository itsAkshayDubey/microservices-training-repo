package demo.controller;

import java.awt.print.Pageable;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import demo.common.TransactionRequest;
import demo.common.TransactionResponse;
import demo.entity.Order;
import demo.response.OrderFindAllResponse;
import demo.service.OrderService;

@RestController
@RequestMapping("/order")
public class OrderController 
{
	@Autowired
	private OrderService service;
	
	@PostMapping("/bookOrder")
	@ResponseStatus(code = HttpStatus.CREATED)
	public TransactionResponse bookOrder(@Valid @RequestBody TransactionRequest request)
	{
		return service.saveOrder(request);
		//return service.saveOrder(request).getOrder().getId();
	}
	
	@GetMapping("/orders/{orderId}")
	public Order getOrderById(@PathVariable int orderId) {
		return service.findOrderByOrderId(orderId);
		
	}
	
	@GetMapping("/orders/store/{storeId}")
	public List<Order> getOrderByStoreId(@PathVariable int storeId) {
		return service.findOrderByStoreId(storeId);
	}
	
	@GetMapping("/orders/customer/{customerId}")
	public List<Order> getOrderByCustomerId(@PathVariable int customerId) {
		return service.findOrderByCustomerId(customerId);
	}
	
	@GetMapping("/orders/status/{status}")
	public List<Order> getOrderByStatus(@PathVariable String status){
		return service.findOrderByStatus(status);
	}
	
	@GetMapping("/allOrders")
	public OrderFindAllResponse getAllOrders(){
		return service.getAllOrders();
	}
	
	@GetMapping("/allOrders/sortBy/status/{sort}")
	public OrderFindAllResponse getAllOrdersSortByStatus(@PathVariable String sort) {
		return service.getAllOrderSortByStatus(sort);
	}
	
	@GetMapping("/allOrders/sortBy/order-date/{sort}")
	public OrderFindAllResponse getAllOrdersSortByOrderDate(@PathVariable String sort) {
		return service.getAllOrderSortByStatus(sort);
	}
	
	@GetMapping("/allOrders/pageNo/{pageNo}/pageElements/{pageElement}")
	public Page<Order> getAllOrders(@PathVariable int pageNo, @PathVariable int pageElement){
		
		org.springframework.data.domain.Pageable pageable  = PageRequest.of(pageNo, pageElement);
	
		return service.findAllPaginatedOrders(pageable);
		
	}
	
}

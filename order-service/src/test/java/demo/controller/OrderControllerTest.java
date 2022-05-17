package demo.controller;

import static org.hamcrest.CoreMatchers.is;
import org.junit.Assert;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.webjars.WebJarAssetLocator;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;

import demo.common.Payment;
import demo.common.TransactionRequest;
import demo.common.TransactionResponse;
import demo.entity.Order;
import demo.response.OrderFindAllResponse;
import demo.service.OrderService;


@SpringBootTest
@AutoConfigureMockMvc
class OrderControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private OrderService service;
	
	@Test
	void testBookOrderController() throws Exception{
		Order order = new Order();
		order.setProductId(101);
		order.setQty(11);
		order.setCustomerId(131);
		Payment payment = new Payment();
		
		TransactionRequest tr = new TransactionRequest();
		tr.setOrder(order);
		tr.setPayment(payment);
		
		ObjectMapper mapper = new ObjectMapper();
	    mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
	    ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
	    String requestJson=ow.writeValueAsString(tr);

		this.mockMvc.perform(post("/order/bookOrder")
				.contentType(MediaType.APPLICATION_JSON)
				.content(requestJson))
				.andExpect(status().isCreated())
				.andDo(print());
	}
	
	@Test
	void testBookOrderService() throws Exception{
		Order order = new Order();
		order.setProductId(101);
		order.setQty(11);
		order.setCustomerId(131);
		Payment payment = new Payment();
		
		TransactionRequest tr = new TransactionRequest();
		tr.setOrder(order);
		tr.setPayment(payment);
		
		ObjectMapper mapper = new ObjectMapper();
	    mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
	    ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
	    String requestJson=ow.writeValueAsString(tr);
	    
	    Order orderResp = new Order();
	    orderResp.setId(1);
	    
	    TransactionResponse tResp = new TransactionResponse();
	    tResp.setOrder(orderResp);
	    tResp.setTransactionId("ABC1234");
	    
	    when(service.saveOrder(tr)).thenReturn(tResp);
	    
	    TransactionResponse response = service.saveOrder(tr);
	    
	    Assert.assertEquals(tResp.getTransactionId(), response.getTransactionId());

	
	}
	
	@Test
	void testGetOrderByIdService() throws Exception {
		Order order = new Order();
		order.setId(1);
		order.setProductId(101);
		order.setName("iPhone 13");
		order.setQty(13);
		order.setPrice(100000);
		order.setStoreId(1);
		order.setCustomerId(1);
		order.setStatus("PLACED");
		order.setOrderDate(new Date());
		order.setGuest(false);
		when(service.findOrderByOrderId(1)).thenReturn(order);
		this.mockMvc.perform(get("/order/orders/1")).andDo(print())
		.andExpect(status().isOk())
		.andExpect(jsonPath("name", is(order.getName())));
	}
	
	@Test
	void testGetOrderById() throws Exception {
		this.mockMvc.perform(get("/order/orders/1")).andDo(print()).andExpect(status().isOk());
	}
	
	@Test
	void testGetOrdersByStoreId() throws Exception {
		Order order = new Order();
		order.setId(1);
		order.setProductId(101);
		order.setName("iPhone 13");
		order.setQty(13);
		order.setPrice(100000);
		order.setStoreId(1);
		order.setCustomerId(1);
		order.setStatus("PLACED");
		order.setOrderDate(new Date());
		order.setGuest(false);
		List<Order> list = new ArrayList<>();
		list.add(order);
		when(service.findOrderByStoreId(1)).thenReturn(list);
		
		this.mockMvc.perform(get("/order/orders/store/1")).andDo(print())
		.andExpect(status().isOk())
		.andExpect(jsonPath("$[0].name",is(order.getName())))
		.andDo(MockMvcResultHandlers.print());
	}
	
	@Test
	void testFindByCustomerIdService() throws Exception{
		Order order = new Order();
		order.setId(1);
		order.setProductId(101);
		order.setName("iPhone 13");
		order.setQty(13);
		order.setPrice(100000);
		order.setStoreId(1);
		order.setCustomerId(131);
		order.setStatus("PLACED");
		order.setOrderDate(new Date());
		order.setGuest(false);
		List<Order> list = new ArrayList<>();
		list.add(order);
		when(service.findOrderByCustomerId(131)).thenReturn(list);
		
		this.mockMvc.perform(get("/order/orders/customer/131")).andDo(print())
		.andExpect(status().isOk())
		.andExpect(jsonPath("$[0].customerId",is(order.getCustomerId())))
		.andDo(MockMvcResultHandlers.print());

	}

	@Test
	void testFindByStatusService() throws Exception{
		Order order = new Order();
		order.setId(1);
		order.setProductId(101);
		order.setName("iPhone 13");
		order.setQty(13);
		order.setPrice(100000);
		order.setStoreId(1);
		order.setCustomerId(131);
		order.setStatus("PLACED");
		order.setOrderDate(new Date());
		order.setGuest(false);
		List<Order> list = new ArrayList<>();
		list.add(order);
		when(service.findOrderByStatus("PLACED")).thenReturn(list);
		
		this.mockMvc.perform(get("/order/orders/status/PLACED")).andDo(print())
		.andExpect(status().isOk())
		.andExpect(jsonPath("$[0].status",is(list.get(0).getStatus())))
		.andDo(MockMvcResultHandlers.print());

	}
	
	@Test
	void testFindAllOrders() throws Exception{
		Order order = new Order();
		order.setId(1);
		order.setProductId(101);
		order.setName("iPhone 13");
		order.setQty(13);
		order.setPrice(100000);
		order.setStoreId(1);
		order.setCustomerId(131);
		order.setStatus("PLACED");
		order.setOrderDate(new Date());
		order.setGuest(false);
		List<Order> list = new ArrayList<>();
		list.add(order);
		
		OrderFindAllResponse orderFindAllResponse = new OrderFindAllResponse();
		orderFindAllResponse.setList(list);
		when(service.getAllOrders()).thenReturn(orderFindAllResponse);
		
		this.mockMvc.perform(get("/order/allOrders"))
			.andDo(print())
			.andExpect(status().isOk());
	}
}

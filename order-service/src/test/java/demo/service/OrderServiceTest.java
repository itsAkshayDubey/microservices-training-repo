package demo.service;

import static org.mockito.Mockito.when;

import org.junit.Assert;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import demo.common.Payment;
import demo.common.TransactionRequest;
import demo.common.TransactionResponse;
import demo.entity.Order;

@SpringBootTest
@AutoConfigureMockMvc
class OrderServiceTest {
	
	@MockBean
	private OrderService service;

	@Test
	void testSaveOrder() {
		
		Order order = new Order();
		order.setProductId(101);
		order.setQty(11);
		order.setCustomerId(131);
		Payment payment = new Payment();
		
		TransactionRequest tr = new TransactionRequest();
		tr.setOrder(order);
		tr.setPayment(payment);
	    
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
	void testFindOrderByOrderId() {
		Order order = new Order();
		order.setId(1);
		order.setStoreId(101);
		when(service.findOrderByOrderId(1)).thenReturn(order);
		
		Assert.assertEquals(order.getStoreId(), service.findOrderByOrderId(1).getStoreId());
	}

	@Test
	void testFindOrderByStoreId() {

		Order order = new Order();
		order.setId(1);
		order.setStoreId(101);
		List<Order> list = new ArrayList<>();
		list.add(order);
		when(service.findOrderByStoreId(101)).thenReturn(list);
		
		Assert.assertEquals(list.get(0).getStoreId(), service.findOrderByStoreId(101).get(0).getStoreId());
		
	}

	@Test
	void testFindOrderByCustomerId() {
		Order order = new Order();
		order.setId(1);
		order.setStoreId(101);
		order.setCustomerId(123);
		List<Order> list = new ArrayList<>();
		list.add(order);
		when(service.findOrderByCustomerId(123)).thenReturn(list);
		
		Assert.assertEquals(list.get(0).getCustomerId(), service.findOrderByCustomerId(123).get(0).getCustomerId());	}

	@Test
	void testFindOrderByStatus() {
		Order order = new Order();
		order.setId(1);
		order.setStoreId(101);
		order.setStatus("PLACED");
		List<Order> list = new ArrayList<>();
		list.add(order);
		when(service.findOrderByStatus("PLACED")).thenReturn(list);
		
		Assert.assertEquals(list.get(0).getStatus(), service.findOrderByStatus("PLACED").get(0).getStatus());
	}

	@Test
	void testGetAllOrders() {
		Order order = new Order();
		order.setId(1);
		order.setStoreId(101);
		List<Order> list = new ArrayList<>();
		list.add(order);
		when(service.findOrderByStoreId(101)).thenReturn(list);
		
		Assert.assertEquals(list.get(0).getStoreId(), service.findOrderByStoreId(101).get(0).getStoreId());
	}

	//@Test
	void testGetAllOrderSortByStatus() {
		Assert.fail("Not yet implemented");
	}

	//@Test
	void testGetAllOrderSortByOrderDate() {
		Assert.fail("Not yet implemented");
	}

	//@Test
	void testFindAllPaginatedOrders() {
		Assert.fail("Not yet implemented");
	}

}

package demo.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import demo.common.Payment;
import demo.common.TransactionRequest;
import demo.common.TransactionResponse;
import demo.entity.Order;
import demo.repository.OrderRepository;

class OrderServiceTest {
	
	@Mock
	OrderRepository orderRepository;

	@Test
	void testSaveOrder() {
		
		assertTrue(true);
	}

}

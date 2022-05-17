package demo.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;


import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import demo.entity.Payment;
import demo.util.TransactionIdGenerator;

@SpringBootTest
@AutoConfigureMockMvc
class PaymentServiceTest {
	
	@MockBean
	PaymentService ser;
	
	@MockBean
	TransactionIdGenerator trans;

	@Test
	void testDoPayment() {
		Payment payment = new Payment();
		payment.setOrderId(1);		
		
		
		Payment paymentResponse = new Payment();
		paymentResponse.setPaymentStatus("success");
		paymentResponse.setOrderId(1);
		when(ser.doPayment(payment)).thenReturn(paymentResponse);
		
		assertEquals(payment.getOrderId(), ser.doPayment(payment).getOrderId());
		assertEquals("success", ser.doPayment(payment).getPaymentStatus());
		
		
	}

	@Test
	void testFindPaymentHistoryByOrderId() {

		int id = 1;
		
		Payment payment = (new Payment());
		payment.setOrderId(id);
		
		when(ser.findPaymentHistoryByOrderId(id)).thenReturn(payment);
		assertEquals(payment.getOrderId(), ser.findPaymentHistoryByOrderId(id).getOrderId());
		
		
		
	}
	
	@Test
	void testGenerateTransactionId() {
		
		when(trans.generateTransactionId()).thenReturn("ABC123");
		
		String output = trans.generateTransactionId();
		
		assertEquals("ABC123", output);
		
	}

}

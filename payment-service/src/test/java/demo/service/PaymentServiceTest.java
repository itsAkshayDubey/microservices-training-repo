package demo.service;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import demo.entity.Payment;
import demo.repository.PaymentRepository;

@SpringBootTest
@AutoConfigureMockMvc
class PaymentServiceTest {
	
	@MockBean
	PaymentRepository repo;
	
	@Autowired
	MockMvc mockMvc;

	@Test
	void testDoPayment() {
		Payment payment = new Payment();
		payment.setOrderId(1);
		payment.setPaymentStatus("success");
		
		when(repo.save(payment)).thenReturn(payment);
		
		assertEquals(payment.getOrderId(), repo.save(payment).getOrderId());
		assertEquals("success", payment.getPaymentStatus());
		
		
	}

	@Test
	void testFindPaymentHistoryByOrderId() {

		int id = 1;
		
		Optional<Payment> payment = Optional.ofNullable(new Payment());
		if(payment.isPresent())
			payment.get().setOrderId(id);
		
		when(repo.findById(id)).thenReturn(payment);
		if(payment.isPresent())
		assertEquals(payment.get().getOrderId(), repo.findById(id).get().getOrderId());
		
		
		
	}

}

package demo.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;

import demo.entity.Payment;
import demo.service.PaymentService;

@SpringBootTest
@AutoConfigureMockMvc
class PaymentControllerTest {
	
	@MockBean
	PaymentService service;
	
	@Autowired
	MockMvc mockMvc;

	@Test
	void testDoPayment() throws Exception {
		
		Payment payment = new Payment();
		payment.setOrderId(1);
		payment.setPaymentStatus("success");
		
		ObjectMapper mapper = new ObjectMapper();
	    mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
	    ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
	    String requestJson=ow.writeValueAsString(payment);
		
		this.mockMvc.perform(post("/payment/doPayment")
			.contentType(MediaType.APPLICATION_JSON)
			.content(requestJson))
			.andDo(print())
			.andExpect(status().isCreated());
	}

	@Test
	void testFindPaymentHistoryByOrderId() throws Exception {
		
		Payment payment = new Payment();
		
		payment.setOrderId(1);
		when(service.findPaymentHistoryByOrderId(1)).thenReturn(payment);

		this.mockMvc.perform(get("/payment/order/0"))
			.andExpect(status().isOk());
		
	}

}

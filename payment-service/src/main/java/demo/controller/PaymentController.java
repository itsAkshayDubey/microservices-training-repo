package demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import demo.entity.Payment;
import demo.service.PaymentService;

@RestController
@RequestMapping("/payment")
public class PaymentController 
{
	@Autowired
	private PaymentService service;
	
	@PostMapping("/doPayment")
	@ResponseStatus(code = HttpStatus.CREATED)
	public Payment doPayment(@RequestBody Payment payment)
	{
		return service.doPayment(payment);
	}
	
	@GetMapping("/order/{orderId}")
	public Payment findPaymentHistoryByOrderId(@PathVariable int orderId)
	{
		return service.findPaymentHistoryByOrderId(orderId);
	}
	
}

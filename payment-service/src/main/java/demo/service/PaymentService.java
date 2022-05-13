package demo.service;

import java.util.Random;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.entity.Payment;
import demo.repository.PaymentRepository;
import demo.util.TransactionIdGenerator;

@Service
public class PaymentService 
{
	@Autowired
	private PaymentRepository repository;
	
	@Autowired
	private TransactionIdGenerator transactionIdGenerator;
	
	public Payment doPayment(Payment payment)
	{
		payment.setPaymentStatus(paymentProcessing());
		payment.setTransactionId(transactionIdGenerator.generateTransactionId().toUpperCase());
		return repository.save(payment);
	}
	
	public String paymentProcessing()
	{
		// for others payment
		return new Random().nextBoolean()?"success":"failure";
	}

	public Payment findPaymentHistoryByOrderId(int orderId) {
		
		// TODO Auto-generated method stub
		return repository.findByOrderId(orderId);
	}
}

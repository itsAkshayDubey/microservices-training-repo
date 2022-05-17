package demo.service;

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
		payment.setPaymentStatus("success");
		payment.setTransactionId(transactionIdGenerator.generateTransactionId().toUpperCase());
		return repository.save(payment);
	}

	public Payment findPaymentHistoryByOrderId(int orderId) {
		
		return repository.findByOrderId(orderId);
	}
}

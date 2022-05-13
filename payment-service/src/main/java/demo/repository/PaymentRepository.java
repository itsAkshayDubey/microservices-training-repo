package demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import demo.entity.*;
public interface PaymentRepository extends JpaRepository<Payment,Integer>
{

	Payment findByOrderId(int orderId);

}

package demo.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import demo.entity.Order;

public interface OrderRepository extends JpaRepository<Order,String> 
{
	public Order findById(int orderId);

	public List<Order> findByStoreId(int storeId);

	public List<Order> findByCustomerId(int customerId);

	public List<Order> findByStatus(String status);	

	public List<Order> findAllByOrderByStatusAsc();

	public List<Order> findAllByOrderByStatusDesc();

	public List<Order> findAllByOrderByOrderDateAsc();

	public List<Order> findAllByOrderByOrderDateDesc();

	public Page<Order> findAll(Pageable p);

}

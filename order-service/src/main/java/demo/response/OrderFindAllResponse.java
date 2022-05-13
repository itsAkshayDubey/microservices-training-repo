package demo.response;

import java.util.List;

import demo.entity.Order;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderFindAllResponse {

	private List<Order> list;
	private String message;
	public List<Order> getList() {
		return list;
	}
	public void setList(List<Order> list) {
		this.list = list;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	
	
}

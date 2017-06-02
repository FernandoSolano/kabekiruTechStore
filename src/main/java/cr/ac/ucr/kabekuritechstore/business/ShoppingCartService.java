package cr.ac.ucr.kabekuritechstore.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cr.ac.ucr.kabekuritechstore.data.ProductDao;
import cr.ac.ucr.kabekuritechstore.data.ShoppingCartDao;
import cr.ac.ucr.kabekuritechstore.domain.Order;

@Service
public class ShoppingCartService {

	@Autowired
	private ShoppingCartDao shoppingCartDao;
	
	public Order insertOrder(Order order){
		return shoppingCartDao.insertOrder(order);
	}
}

package cr.ac.ucr.kabekuritechstore.business;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cr.ac.ucr.kabekuritechstore.data.ProductDao;
import cr.ac.ucr.kabekuritechstore.domain.Product;

@Service
public class ProductService {
	
	@Autowired
	private ProductDao productDao;
	
	public List<Product> findAll(){
		return productDao.findAll();
	}
	
	public List<Product> findProductsByCategory(int category_id){
		return productDao.findProductsByCategory(category_id);
	}
	
	public List<Product> findProductsByName(String product_name){
		return productDao.findProductsByName(product_name);
	}
	
	public List<Product> findProductsByRangeOfPriceAndName(String product_name, int minPrice, int maxPrice){
		return productDao.findProductsByRangeOfPriceAndName(product_name, minPrice, maxPrice);
	}
}

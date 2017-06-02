package cr.ac.ucr.kabekuritechstore.business;

import java.sql.SQLException;
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

	public Product findProductById(int productId){
		return productDao.findProductById(productId);
	}
	
	public Product save(Product product) throws SQLException{
		return productDao.save(product);
	}
   
	public void deleteProductById(int id) throws SQLException {
		productDao.deleteProductById(id);
	}
	
	public void updateProduct(Product product) throws SQLException {
		productDao.updateProduct(product);
	}
}

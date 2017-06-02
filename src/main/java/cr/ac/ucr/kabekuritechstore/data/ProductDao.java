package cr.ac.ucr.kabekuritechstore.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import cr.ac.ucr.kabekuritechstore.domain.Product;

@Repository
public class ProductDao {
	private JdbcTemplate jdbcTemplate;
	private SimpleJdbcCall simpleJdbcCallProduct;
	private SimpleJdbcCall simpleJdbcCallDelete;
	private SimpleJdbcCall simpleJdbcCallUpdate;
	
	@Autowired
	private DataSource dataSource;
	
	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		this.jdbcTemplate = new JdbcTemplate(dataSource);
		this.simpleJdbcCallProduct = new SimpleJdbcCall(dataSource).withProcedureName("sp_product_insert");
	}
	
	public List<Product> findAll(){
		String sqlSelect ="CALL sp_product_get_all";
		return jdbcTemplate.query(sqlSelect, new findAllProductsExtractor());
	}
	
	public List<Product> findProductsByCategory(int category_id){
		String sqlSelect = "CALL sp_product_get_by_category ("+category_id+")";
		return jdbcTemplate.query(sqlSelect, new findAllProductsExtractor());
	}
	
	public List<Product> findProductsByName(String product_name){
		String sqlSelect = "CALL sp_product_get_by_name ('"+product_name+"')";
		return jdbcTemplate.query(sqlSelect, new findAllProductsExtractor());
	}
	
	public List<Product> findProductsByRangeOfPriceAndName(String product_name, int minPrice, int maxPrice){
		String sqlSelect = "SELECT p.id, p.name, p.image_url, p.description,"
				+ " p.price, p.units_on_stock, c.id AS category_id, c.name AS category_name"
				+ " FROM products p, categories c"
				+ " WHERE p.category_id=c.id AND p.name LIKE '%"+product_name+"%' AND p.price BETWEEN "+minPrice+" AND "+maxPrice;
		return jdbcTemplate.query(sqlSelect, new findAllProductsExtractor());
	}
	
	public Product findProductById(int productId){
		String sqlSelect ="CALL sp_product_get("+productId+")";
		List<Product> products = jdbcTemplate.query(sqlSelect, new findAllProductsExtractor());
		if(products.isEmpty()){
			return null;
		}else{
			return products.get(0);
		}
	}
	
	@Transactional
	public Product save(Product product) throws SQLException{
		SqlParameterSource parameterSource = new MapSqlParameterSource()
				.addValue("p_category_id", product.getCategory().getId())						
				.addValue("p_name", product.getName())
				.addValue("p_description", product.getDescription())
				.addValue("p_price", product.getPrice())
				.addValue("p_units_on_stock", product.getUnitsOnStock())
				.addValue("p_image_url",product.getImage_url());
		Map<String, Object> outParameter = simpleJdbcCallProduct.execute(parameterSource);
		product.setId((Integer.parseInt((outParameter.get("p_id").toString()))));
		return product;
	}
	
	@Transactional
	public void deleteProductById(int id) throws SQLException {
		simpleJdbcCallDelete = new SimpleJdbcCall(dataSource)
				.withProcedureName("sp_product_get");
		SqlParameterSource parameterSource = new MapSqlParameterSource()
				.addValue("id", id);
		simpleJdbcCallDelete.execute(parameterSource);
	}
	
	@Transactional
	public void updateProduct(Product product) throws SQLException {
		simpleJdbcCallUpdate = new SimpleJdbcCall(dataSource)
				.withProcedureName("sp_product_update");
		SqlParameterSource parameterSource = new MapSqlParameterSource()
				.addValue("p_category_id", product.getCategory().getId())						
				.addValue("p_name", product.getName())
				.addValue("p_description", product.getDescription())
				.addValue("p_price", product.getPrice())
				.addValue("p_units_on_stock", product.getUnitsOnStock())
				.addValue("p_image_url",product.getImage_url());
		simpleJdbcCallUpdate.execute(parameterSource);
	}
	
	private static final class findAllProductsExtractor implements ResultSetExtractor<List<Product>> {
		@Override
		public List<Product> extractData(ResultSet rs) throws SQLException, DataAccessException {
			Map<Integer, Product> map = new HashMap<Integer, Product>();
			Product product = new Product();
			while (rs.next()) {
				Integer id = rs.getInt("id");
				product = map.get(id);
				if (product == null) {
					product = new Product();
					product.setId(id);
					product.setName(rs.getString("name"));
					product.setDescription(rs.getString("description"));
					product.setPrice(rs.getFloat("price"));
					product.setUnitsOnStock(rs.getInt("units_on_stock"));
					product.setImage_url(rs.getString("image_url"));
					product.getCategory().setId(rs.getInt("category_id"));
					product.getCategory().setName(rs.getString("category_name"));
					map.put(id, product);
				}
			}
			return new ArrayList<Product>(map.values());
		}
	}
}

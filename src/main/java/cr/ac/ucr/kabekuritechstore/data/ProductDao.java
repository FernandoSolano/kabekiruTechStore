package cr.ac.ucr.kabekuritechstore.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import cr.ac.ucr.kabekuritechstore.domain.Product;

@Repository
public class ProductDao {
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	public List<Product> findAll(){
		String sqlSelect = "SELECT p.id, p.name, p.image_url, p.description,"
				+ " p.price, p.units_on_stock, c.id AS category_id, c.name AS category_name"
				+ " FROM products p, categories c"
				+ " WHERE p.category_id=c.id";
		return jdbcTemplate.query(sqlSelect, new findAllProductsExtractor());
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

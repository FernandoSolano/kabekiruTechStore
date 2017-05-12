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
		String sqlSelect = "";
		return jdbcTemplate.query(sqlSelect, new findAllProductsExtractor());
	}
	
	private static final class findAllProductsExtractor implements ResultSetExtractor<List<Product>> {
		@Override
		public List<Product> extractData(ResultSet rs) throws SQLException, DataAccessException {
			Map<Integer, Product> map = new HashMap<Integer, Product>();
			Product product = new Product();
			while (rs.next()) {
				Integer codigo = rs.getInt("num_libro");
				product = map.get(codigo);
				if (product == null) {
					/*product = new Libro();
					product.setCodigo(codigo);
					product.setTitulo(rs.getString("titulo_libro"));
					product.setAnoPublicacion(rs.getInt("ano_publicacion"));
					product.getPublicador().setNombre(rs.getString("nombre_publicador"));*/
					map.put(codigo, product);
				}
			}
			return new ArrayList<Product>(map.values());
		}
	}
}

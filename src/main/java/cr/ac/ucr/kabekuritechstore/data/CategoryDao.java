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
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import cr.ac.ucr.kabekuritechstore.domain.Category;

@Repository
public class CategoryDao {
	private JdbcTemplate jdbcTemplate;
	private SimpleJdbcCall simpleJdbcCall;
	
	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
		this.simpleJdbcCall = new SimpleJdbcCall(dataSource).withProcedureName("sp_category_insert");
	}
	
	public List<Category> findAll(){
		String sqlSelect = "SELECT * FROM categories";
		return jdbcTemplate.query(sqlSelect, new findAllCategoriesExtractor());
	}
	
	public Category insertCategory(Category category){
		SqlParameterSource parameterSource = new MapSqlParameterSource()
				.addValue("name", category.getName());
		Map<String, Object> outParameter = simpleJdbcCall.execute(parameterSource);
		category.setId(Integer.parseInt((outParameter.get("id").toString())));
		return category;
	}
	
	private static final class findAllCategoriesExtractor implements ResultSetExtractor<List<Category>> {
		@Override
		public List<Category> extractData(ResultSet rs) throws SQLException, DataAccessException {
			Map<Integer, Category> map = new HashMap<Integer, Category>();
			Category category = new Category();
			while (rs.next()) {
				Integer id = rs.getInt("id");
				category = map.get(id);
				if (category == null) {
					category = new Category();
					category.setId(id);
					category.setName(rs.getString("name"));
					map.put(id, category);
				}
			}
			return new ArrayList<Category>(map.values());
		}
	}
}

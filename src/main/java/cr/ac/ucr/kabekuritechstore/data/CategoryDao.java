package cr.ac.ucr.kabekuritechstore.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;
import javax.swing.JOptionPane;

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

import cr.ac.ucr.kabekuritechstore.domain.Category;

@Repository
public class CategoryDao {
	private JdbcTemplate jdbcTemplate;
	private SimpleJdbcCall simpleJdbcCall;
	private SimpleJdbcCall simpleJdbcCallDelete;
	private SimpleJdbcCall simpleJdbcCallUpdate;
	
	@Autowired
	private DataSource dataSource;
	
	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
		this.simpleJdbcCall = new SimpleJdbcCall(dataSource).withProcedureName("sp_category_insert");
	}
	
	public List<Category> findAll(){
		String sqlSelect = "SELECT * FROM categories";
		return jdbcTemplate.query(sqlSelect, new findAllCategoriesExtractor());
	}
	
	public Category findCategoryByID(int id) {
		String sqlSelect = "CALL sp_category_get ('"+id+"')";
		Map<String, Object> values = jdbcTemplate.queryForMap(sqlSelect);
		return new Category((int) values.get("id"), (String) values.get("name"));
	}
	
	public List<Category> findCategoriesByName(String name) {
		String sqlSelect = "CALL sp_category_get_by_name ('"+name+"')";
		return jdbcTemplate.query(sqlSelect, new findAllCategoriesExtractor());
	}
	
	public Category insertCategory(Category category){
		System.out.println("******************"+category.getName());
		SqlParameterSource parameterSource = new MapSqlParameterSource()
				.addValue("p_name", category.getName());
		Map<String, Object> outParameter = simpleJdbcCall.execute(parameterSource);
		category.setId(Integer.parseInt((outParameter.get("p_id").toString())));
		return category;
	}
	
	@Transactional
	public void deleteCategoryById(int id) throws SQLException {
		simpleJdbcCallDelete = new SimpleJdbcCall(dataSource)
				.withProcedureName("sp_category_delete");
		SqlParameterSource parameterSource = new MapSqlParameterSource()
				.addValue("p_id", id);
		simpleJdbcCallDelete.execute(parameterSource);
	}
 
	@Transactional
	public void updateCategoria(Category category) throws SQLException {
		simpleJdbcCallUpdate = new SimpleJdbcCall(dataSource)
				.withProcedureName("sp_category_update");
		SqlParameterSource parameterSource = new MapSqlParameterSource()
				.addValue("p_id", category.getId())
				.addValue("p_name", category.getName())				
				;
		
		simpleJdbcCallUpdate.execute(parameterSource);
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

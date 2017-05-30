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

import cr.ac.ucr.kabekuritechstore.domain.Role;
import cr.ac.ucr.kabekuritechstore.domain.User;

@Repository
public class UserDao {
	
	private JdbcTemplate jdbcTemplate;
	private SimpleJdbcCall simpleJdbcCall;
	
	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
		this.simpleJdbcCall = new SimpleJdbcCall(dataSource).withProcedureName("sp_user_insert");
	}
	
	public User insertUser(User user){
		//TODO review and test this code
		SqlParameterSource parameterSource = new MapSqlParameterSource()
				.addValue("first_name", user.getFirstName())
				.addValue("last_name", user.getLastName())
				.addValue("email", user.getEmail())
				.addValue("login", user.getUserName())
				.addValue("password", user.getPassword())
				.addValue("enabled", 0);
		Map<String, Object> outParameter = simpleJdbcCall.execute(parameterSource);
		user.setUserID(Integer.parseInt((outParameter.get("id").toString())));
		return user;
	}
	
	public User userValidation(String username, String password){
		String sqlExcute = "CALL sp_user_validation('" + username + "', '" + password +"')";
		List<User> users = jdbcTemplate.query(sqlExcute, new UserWithRoleExtractor());
		
		if(users.size() > 0){
			return users.get(0);
		}
		return null;
	}
	
	private static final class UserWithRoleExtractor implements ResultSetExtractor<List<User>> {

		@Override
		public List<User> extractData(ResultSet rs) throws SQLException, DataAccessException {
			Map<Integer, User> map = new HashMap<Integer, User>();
			User user = null;
			while (rs.next()) {
				int id = rs.getInt("id");
				user = map.get(id);

				if (user == null) {
					user = new User();
					user.setUserID(id);
					user.setFirstName(rs.getString("first_name"));
					user.setLastName(rs.getString("last_name"));
					user.setEmail(rs.getString("email"));
					user.setUserName(rs.getString("username"));
					user.setPassword(rs.getString("password"));
					user.setEnabled(rs.getBoolean("enabled"));
					map.put(id, user);
				}
				
				int roleId = rs.getInt("role_id");
				if(roleId>0){
					Role role = new Role();
					role.setRolID(roleId);
					role.setType(rs.getString("type"));
					
					user.setRoleId(role);
				} 
			}
			return new ArrayList<User>(map.values());
		}
	}
}

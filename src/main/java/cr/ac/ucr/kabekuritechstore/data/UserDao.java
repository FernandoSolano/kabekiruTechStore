package cr.ac.ucr.kabekuritechstore.data;

import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

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
}

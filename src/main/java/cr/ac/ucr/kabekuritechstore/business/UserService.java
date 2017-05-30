package cr.ac.ucr.kabekuritechstore.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cr.ac.ucr.kabekuritechstore.data.UserDao;
import cr.ac.ucr.kabekuritechstore.domain.User;

@Repository
public class UserService {
	
	@Autowired
	private UserDao userDao;
	
	public User insertUser(User user){
		return userDao.insertUser(user);
	}
	
	public User userValidation(String username, String password){
		return userDao.userValidation(username, password);
	}
}

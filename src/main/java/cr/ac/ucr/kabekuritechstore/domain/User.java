package cr.ac.ucr.kabekuritechstore.domain;

import java.util.LinkedList;
import java.util.List;

public class User {
	
	private int userID;
	private String firstName;
	private String lastName;
	private String email;
	private String userName;
	private String password;
	private boolean enabled;
	private Role role;
	private LinkedList<ShippingAddress> addresses;
	
	
	public User() {
		role = new Role();
		addresses = new LinkedList<>();
	}


	public int getUserID() {
		return userID;
	}


	public void setUserID(int userID) {
		this.userID = userID;
	}


	public String getFirstName() {
		return firstName;
	}


	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	public String getLastName() {
		return lastName;
	}


	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}
	

	public Role getRoleId() {
		return role;
	}


	public void setRoleId(Role role) {
		this.role = role;
	}


	public boolean isEnabled() {
		return enabled;
	}


	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	
	
	

}

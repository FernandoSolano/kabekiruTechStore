package cr.ac.ucr.kabekuritechstore.domain;

import java.sql.Date;

public class ShoppingCart {
	
	private int carID;
	private User user;
	private Date dateCreate;
	
	
	public ShoppingCart() {
		
	}


	public int getCarID() {
		return carID;
	}


	public void setCarID(int carID) {
		this.carID = carID;
	}


	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}


	public Date getDateCreate() {
		return dateCreate;
	}


	public void setDateCreate(Date dateCreate) {
		this.dateCreate = dateCreate;
	}
	
	
	
	

}
 

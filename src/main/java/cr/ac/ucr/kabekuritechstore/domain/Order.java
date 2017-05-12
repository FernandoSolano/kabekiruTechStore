package cr.ac.ucr.kabekuritechstore.domain;

import java.sql.Date;

public class Order {
	
	private int orderID;
	private User user;
	private int orderStatusID;
	private Date orderDate;
	private Date shipDate;
	private String trackingNumber;
	private float total;
	
	public Order() {
		
	}

	public int getOrderID() {
		return orderID;
	}

	public void setOrderID(int orderID) {
		this.orderID = orderID;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public int getOrderStatusID() {
		return orderStatusID;
	}

	public void setOrderStatusID(int orderStatusID) {
		this.orderStatusID = orderStatusID;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public Date getShipDate() {
		return shipDate;
	}

	public void setShipDate(Date shipDate) {
		this.shipDate = shipDate;
	}

	public String getTrackingNumber() {
		return trackingNumber;
	}

	public void setTrackingNumber(String trackingNumber) {
		this.trackingNumber = trackingNumber;
	}

	public float getTotal() {
		return total;
	}

	public void setTotal(float total) {
		this.total = total;
	}
	
	
	
    
}

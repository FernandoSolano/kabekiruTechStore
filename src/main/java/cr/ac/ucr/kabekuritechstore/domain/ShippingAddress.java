package cr.ac.ucr.kabekuritechstore.domain;

public class ShippingAddress {

  private int shippingAddressID;
  private User user;
  private String addressLine;
  private String addressLine2;
  private String phoneNumber;
  private String phoneNumber2;
  private String city;
  private String state;
  private String zipCode;
  
  
  public ShippingAddress() {
	
  }


public int getShippingAddressID() {
	return shippingAddressID;
}


public void setShippingAddressID(int shippingAddressID) {
	this.shippingAddressID = shippingAddressID;
}


public User getUser() {
	return user;
}


public void setUser(User user) {
	this.user = user;
}


public String getAddressLine() {
	return addressLine;
}


public void setAddressLine(String addressLine) {
	this.addressLine = addressLine;
}


public String getAddressLine2() {
	return addressLine2;
}


public void setAddressLine2(String addressLine2) {
	this.addressLine2 = addressLine2;
}


public String getPhoneNumber() {
	return phoneNumber;
}


public void setPhoneNumber(String phoneNumber) {
	this.phoneNumber = phoneNumber;
}


public String getPhoneNumber2() {
	return phoneNumber2;
}


public void setPhoneNumber2(String phoneNumber2) {
	this.phoneNumber2 = phoneNumber2;
}


public String getCity() {
	return city;
}


public void setCity(String city) {
	this.city = city;
}


public String getState() {
	return state;
}


public void setState(String state) {
	this.state = state;
}


public String getZipCode() {
	return zipCode;
}


public void setZipCode(String zipCode) {
	this.zipCode = zipCode;
}
  
  

}

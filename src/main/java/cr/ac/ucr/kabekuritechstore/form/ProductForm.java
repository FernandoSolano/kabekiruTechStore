package cr.ac.ucr.kabekuritechstore.form;

import javax.validation.constraints.Min;


public class ProductForm {

	
	private int id;
	
	
	@Min(2)
	private int unitsOnStock;
	private int category;
	private String name, description, image_url;
	private float price;
	
	
	public ProductForm() {
		
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public int getUnitsOnStock() {
		return unitsOnStock;
	}


	public void setUnitsOnStock(int unitsOnStock) {
		this.unitsOnStock = unitsOnStock;
	}


	public int getCategory() {
		return category;
	}


	public void setCategory(int category) {
		this.category = category;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public String getImage_url() {
		return image_url;
	}


	public void setImage_url(String image_url) {
		this.image_url = image_url;
	}


	public float getPrice() {
		return price;
	}


	public void setPrice(float price) {
		this.price = price;
	}
	
	
}

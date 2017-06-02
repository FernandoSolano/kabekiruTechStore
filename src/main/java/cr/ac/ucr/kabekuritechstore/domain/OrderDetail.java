package cr.ac.ucr.kabekuritechstore.domain;

public class OrderDetail {
	private int id, quantity;
	private float price;
	private Order order;
	private Product product;
	
	public OrderDetail() {
		this.product = new Product();
		this.order = new Order();
	}

	public OrderDetail(int id, int quantity, float price, Order order, Product product) {
		super();
		this.id = id;
		this.quantity = quantity;
		this.price = price;
		this.order = order;
		this.product = product;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}
	
	
	
}

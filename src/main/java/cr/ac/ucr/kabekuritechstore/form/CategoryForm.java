package cr.ac.ucr.kabekuritechstore.form;

import javax.validation.constraints.Size;



public class CategoryForm {
	
	private int id;
	
	
	@Size(min=2, max=50)
	private String name;

	public CategoryForm() {
		
	}

	

	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}



	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
	
	
	

}

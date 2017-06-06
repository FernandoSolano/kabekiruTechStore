package cr.ac.ucr.kabekuritechstore.controller;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.AntPathMatcher;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import cr.ac.ucr.kabekuritechstore.business.CategoryService;
import cr.ac.ucr.kabekuritechstore.business.ProductService;
import cr.ac.ucr.kabekuritechstore.domain.Product;
import cr.ac.ucr.kabekuritechstore.form.ProductForm;



@Controller
public class ProductController {

	@Autowired
	private ProductService productService;
	@Autowired
	private CategoryService categoryService;
	private Product product;

	private List<Product> products;
	private int cod;
	
	
	
	@RequestMapping(value ="/maintenanceProducts",method=RequestMethod.GET)
	public String star(Model model){
		products =   productService.findProductsByName("");
		model.addAttribute("products",products);
		return "maintenanceProduct";
	}
	
	@RequestMapping(value= "/maintenanceProducts/search", method = RequestMethod.POST)
	 public String search(@RequestParam Map<String,String> requestParams, Model model){
		
		String name = requestParams.get("name");
		products =   productService.findProductsByName(name);
		model.addAttribute("products",products);
		return "maintenanceProduct";
	}
	
	@RequestMapping(value = "deleteProduct/{id}**", method = RequestMethod.GET)
	public String starDelete(@PathVariable int id, HttpServletRequest request, Model model) {
		
		int cod = Integer.parseInt(new AntPathMatcher().extractPathWithinPattern("/{id}/**", request.getRequestURI()));
		
		product = productService.findProductById(cod);
		
		model.addAttribute("id", product.getId());		
		model.addAttribute("name", product.getName());
		
		return "deleteProduct";
	}
	
	@RequestMapping(value = "deleteProduct/confirm", method=RequestMethod.GET)
	public String delete(Model model, @RequestParam Map<String, String> requestParams) {		
		try {
			int productID = Integer.parseInt(requestParams.get("p_id"));
			productService.deleteProductById(productID);
			
			model.addAttribute("mensaje","The product was deleted successfully");
			model.addAttribute("type", 2);
			return "Success";
		} catch (SQLException e) {
			model.addAttribute("mensaje","Oops, an error occurred with the deletion");
			model.addAttribute("type", 2);
			return "Error";
		}
	
	}
	
	@RequestMapping(value = "updateProduct/{id}**", method = RequestMethod.GET)
	public String starUpdate(ProductForm productForm, @PathVariable int id, HttpServletRequest request, Model model) {
		model.addAttribute("categories", categoryService.findAll());
	    cod = Integer.parseInt(new AntPathMatcher().extractPathWithinPattern("/{id}/**", request.getRequestURI()));
		
	    
		Product product = productService.findProductById(cod);
		
		model.addAttribute("category",product.getCategory());
		model.addAttribute("name", product.getName());
		model.addAttribute("unitsOnStock",product.getUnitsOnStock());
		model.addAttribute("description",product.getDescription());
		model.addAttribute("image_url",product.getImage_url());
		model.addAttribute("price",product.getPrice());
		
		
		return "updateProduct";
	}// iniciar ()
	
	
	@RequestMapping(value = "updateProduct/ok", method = RequestMethod.POST)
	public String confirm(@Valid ProductForm productForm, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			return "updateProduct";
		} else {
			Product product =  new Product();
            
            product.setName(productForm.getName());
			product.getCategory().setId((productForm.getIdCategory()));
			product.setUnitsOnStock(productForm.getUnitsOnStock());
            product.setDescription(productForm.getDescription());
            product.setImage_url(productForm.getImage_url());
            product.setPrice(productForm.getPrice());
            product.setId(cod);
			
			try {
				productService.updateProduct(product);
				
			    
				model.addAttribute("mensaje", "Update product correctly");
				model.addAttribute("type", 2);
				return "Success";
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				model.addAttribute("mensaje", "Error with the update");
				
				return "Error";
			}
		}
	}
	
	@RequestMapping(value="/insertProduct", method=RequestMethod.GET)
	public String showForm(ProductForm productForm, Model model){			
		model.addAttribute("categories", categoryService.findAll());
		return "insertProduct";
	}
	
 @RequestMapping(value="/insertProduct/save", method=RequestMethod.POST)
	public String save(@Valid ProductForm productForm, BindingResult bindingResult, Model model) throws SQLException{
		if(bindingResult.hasErrors()){
			
			return "insertProduct";
		}else{
			Product product =  new Product();
            
            product.setName(productForm.getName());
			product.getCategory().setId((productForm.getIdCategory()));
            product.setUnitsOnStock(productForm.getUnitsOnStock());
            product.setDescription(productForm.getDescription());
            product.setImage_url(productForm.getImage_url());
            product.setPrice(productForm.getPrice());
			
            productService.save(product);
			model.addAttribute("mensaje","Product insertion was correct");
			model.addAttribute("type", 2);
			return "Success";
		}
	}

}

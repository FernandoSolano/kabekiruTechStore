package cr.ac.ucr.kabekuritechstore.controller;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

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
import cr.ac.ucr.kabekuritechstore.domain.Category;
import cr.ac.ucr.kabekuritechstore.form.CategoryForm;


@Controller
public class CategoryController {

	@Autowired
	private CategoryService categoryService;
	private Category category;

	private List<Category> categories;
	private int cod;
	
	
	
	@RequestMapping(value ="/maintenanceCategories",method=RequestMethod.GET)
	public String star(Model model){
		
		model.addAttribute("categories",categories);
		
		return "maintenanceCategory";
	}
	
	@RequestMapping(value= "/maintenanceCategories/search", method = RequestMethod.POST)
	 public String search(@RequestParam Map<String,String> requestParams, Model model){
		
		String name = requestParams.get("name");
		 
            categories =   categoryService.findCategoriesByName(name);	
			
			model.addAttribute("categories",categories);
		
		return "maintenanceCategory";
	}
	
	@RequestMapping(value = "deleteCategory/{id}**", method = RequestMethod.GET)
	public String starDelete(@PathVariable int id, HttpServletRequest request, Model model) {
		
		int cod = Integer.parseInt(new AntPathMatcher().extractPathWithinPattern("/{id}/**", request.getRequestURI()));
		category = categoryService.findCategoryByID(cod);
		
		model.addAttribute("id", category.getId());
		model.addAttribute("name", category.getName());
		

		return "DeleteCategory";
	}
	
	@RequestMapping(value = "deleteCategory/delete", method=RequestMethod.GET)
	public String delete(Model model) {		
		try {
			categoryService.deleteCategoryById(category.getId());;
			model.addAttribute("mensaje1","The category was deleted with success");
			model.addAttribute("type", 1);
			return "Success";
		} catch (SQLException e) {
			model.addAttribute("mensaje1","Oops, an error occurred with the deletion");
			return "Error";
		}
	
	}
	
	@RequestMapping(value = "updateCategory/{id}**", method = RequestMethod.GET)
	public String starUpdate(CategoryForm categoryForm, @PathVariable int id, HttpServletRequest request, Model model) {
		
	    cod = Integer.parseInt(new AntPathMatcher().extractPathWithinPattern("/{id}/**", request.getRequestURI()));
		
	    Category categry =  categoryService.findCategoryByID(cod);
		
		
		model.addAttribute("id", categry.getId());
		model.addAttribute("name", categry.getName());
		
		
		return "UpdateCategory";
	}// iniciar ()
	
	
	@RequestMapping(value = "updateCategory/ok", method = RequestMethod.POST)
	public String confirm(@Valid CategoryForm categoryForm, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			return "UpdateCategory";
		} else {
            Category category =  new Category();
            category.setName(categoryForm.getName());
            category.setId(cod);
			
			try {
				categoryService.updateCategoria(category);
			    
				model.addAttribute("mensaje1", "Update categoria correctly");
				model.addAttribute("type", 1);
				return "Success";
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				model.addAttribute("mensaje1", "Error with the update");
				
				return "Error";
			}
		}
	}
	
	@RequestMapping(value="/insertCategory", method=RequestMethod.GET)
	public String showForm(CategoryForm categoryForm, Model model){			
		
		return "InsertCategory";
	}
	
 @RequestMapping(value="/insertCategory/save", method=RequestMethod.POST)
	public String save(@Valid CategoryForm categoryForm, BindingResult bindingResult, Model model) throws SQLException{
		if(bindingResult.hasErrors()){
			return "InsertCategory";
		}else{
			Category category =  new Category();
            category.setName(categoryForm.getName());
			categoryService.insertCategory(category);
			model.addAttribute("mensaje1","Category insertion was correct");
			model.addAttribute("type", 1);
			return "Success";
		}
	}


}

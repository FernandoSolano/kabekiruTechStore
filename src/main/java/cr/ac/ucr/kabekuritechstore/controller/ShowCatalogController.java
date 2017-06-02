package cr.ac.ucr.kabekuritechstore.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import cr.ac.ucr.kabekuritechstore.business.ProductService;

@Controller
public class ShowCatalogController {
	@Autowired
	private ProductService productService;
	
	@RequestMapping(value="/catalog", method=RequestMethod.GET)
	public String init(Model model){
		model.addAttribute("products", productService.findAll());
		return "catalog";
	}
	
	@RequestMapping(value="/catalog/category", method=RequestMethod.GET)
	public String showProductsByCategory(@RequestParam Map<String, String> requestParams, Model model){
		int categoryID=Integer.parseInt(requestParams.get("category_id"));
		model.addAttribute("products", productService.findProductsByCategory(categoryID));
		return "catalog";
	}
}

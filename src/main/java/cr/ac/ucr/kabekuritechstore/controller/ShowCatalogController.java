package cr.ac.ucr.kabekuritechstore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
}

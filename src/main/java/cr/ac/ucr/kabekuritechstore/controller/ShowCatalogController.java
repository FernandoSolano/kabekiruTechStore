package cr.ac.ucr.kabekuritechstore.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import cr.ac.ucr.kabekuritechstore.business.ProductService;
import cr.ac.ucr.kabekuritechstore.domain.Product;

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
	
	@RequestMapping(value="/catalog/product", method=RequestMethod.GET)
	public String showProductsByName(@RequestParam Map<String, String> requestParams, Model model){
		String productName=requestParams.get("product_name");
		String productMinPrice=requestParams.get("min_price");
		String productMaxPrice=requestParams.get("max_price");
		if((productMinPrice.equals("0") || productMinPrice.equals("")) && (productMaxPrice.equals("max") || productMaxPrice.equals(""))){
			model.addAttribute("products", productService.findProductsByName(productName));
			return "catalog";
		}else if((productMaxPrice.equals("max") || productMaxPrice.equals(""))){
			//Call method findProductsByRangeOfPriceAndName in business with min parameter
			model.addAttribute("products", productService.findProductsByRangeOfPriceAndName(productName, Integer.parseInt(productMinPrice), 9999999));
			return "catalog";
		}else if((productMinPrice.equals("0") || productMinPrice.equals(""))){
			//Call method findProductsByRangeOfPriceAndName in business with max parameter
			model.addAttribute("products", productService.findProductsByRangeOfPriceAndName(productName, 0, Integer.parseInt(productMaxPrice)));
			return "catalog";
		}
		//Call method findProductsByRangeOfPriceAndName in business with both parameters and name
		model.addAttribute("products", productService.findProductsByRangeOfPriceAndName(productName, Integer.parseInt(productMinPrice), Integer.parseInt(productMaxPrice)));
		return "catalog;";
	}
	
	@RequestMapping(value = "/viewDetails/{id}/**", method = RequestMethod.GET)
	public String viewDetails(@PathVariable String id, HttpServletRequest request, Model model){
		
		int productId = Integer.parseInt(new AntPathMatcher().extractPathWithinPattern("/{id}/**", request.getRequestURI())); 
		
		model.addAttribute("product", productService.findProductById(productId));
		
		return "productDetail";
	}
}

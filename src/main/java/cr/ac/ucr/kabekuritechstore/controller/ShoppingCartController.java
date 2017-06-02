package cr.ac.ucr.kabekuritechstore.controller;

import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cr.ac.ucr.kabekuritechstore.business.ProductService;
import cr.ac.ucr.kabekuritechstore.domain.Product;

@Controller
public class ShoppingCartController {

	// Lista de productos
	List<Product> shoppingCartList = new LinkedList<Product>();
	
	@Autowired
	ProductService productService;
	
	@RequestMapping(value="/cart", method=RequestMethod.GET)
	public String showCart(Model model){
		model.addAttribute("products", shoppingCartList);
		return "shoppingCart";
	}
	
	@RequestMapping(value = "/addCart/{id}/**", method = RequestMethod.GET)
	public String addToCart(@PathVariable String id, HttpServletRequest request, Model model){
		
		int productId = Integer.parseInt(new AntPathMatcher().extractPathWithinPattern("/{id}/**", request.getRequestURI())); 
		
		Product product = productService.findProductById(productId);
		
		if(product != null){
			shoppingCartList.add(product);			
		}
		
		model.addAttribute("products", productService.findAll());
		
		return "catalog";
	}
	
	@RequestMapping(value = "/quitFromCart/{id}/**", method = RequestMethod.GET)
	public String quitFromCart(@PathVariable String id, HttpServletRequest request, Model model){
		
		int productId = Integer.parseInt(new AntPathMatcher().extractPathWithinPattern("/{id}/**", request.getRequestURI())); 
		
		for (Product product : shoppingCartList) {
			if(product.getId() == productId){
				shoppingCartList.remove(product);
			}
		}
		
		model.addAttribute("products", shoppingCartList);
		
		return "shoppingCart";
	}
}

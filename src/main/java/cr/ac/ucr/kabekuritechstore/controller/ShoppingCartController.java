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
import cr.ac.ucr.kabekuritechstore.business.ShoppingCartService;
import cr.ac.ucr.kabekuritechstore.domain.Order;
import cr.ac.ucr.kabekuritechstore.domain.OrderDetail;
import cr.ac.ucr.kabekuritechstore.domain.Product;

@Controller
public class ShoppingCartController {

	// Lista de productos
	//List<Product> shoppingCartList = new LinkedList<Product>();
	public static List<OrderDetail> orderDetails = new LinkedList<OrderDetail>();
	
	@Autowired
	ProductService productService;
	
	@Autowired
	ShoppingCartService shoppingCartService;
	
	@RequestMapping(value="/cart", method=RequestMethod.GET)
	public String showCart(Model model){
		model.addAttribute("details", orderDetails);
		return "shoppingCart";
	}
	
	@RequestMapping(value = "/addCart/{id}/**", method = RequestMethod.GET)
	public String addToCart(@PathVariable String id, HttpServletRequest request, Model model){
		
		int productId = Integer.parseInt(new AntPathMatcher().extractPathWithinPattern("/{id}/**", request.getRequestURI())); 
		
		Product product = productService.findProductById(productId);
		
		if(product != null){
			
			OrderDetail currentOrderDetail = new OrderDetail();
			
			currentOrderDetail.setId(orderDetails.size() + 1);
			currentOrderDetail.setQuantity(1);
			currentOrderDetail.setPrice(product.getPrice());
			currentOrderDetail.setProduct(product);
			
			orderDetails.add(currentOrderDetail);			
		}
		
		model.addAttribute("products", productService.findAll());
		
		return "catalog";
	}
	
	@RequestMapping(value = "/quitFromCart/{id}/**", method = RequestMethod.GET)
	public String quitFromCart(@PathVariable String id, HttpServletRequest request, Model model){
		
		int detailId = Integer.parseInt(new AntPathMatcher().extractPathWithinPattern("/{id}/**", request.getRequestURI())); 
		
		for (OrderDetail detail : orderDetails) {
			if(detail.getId() == detailId){
				orderDetails.remove(detail);
			}
		}
		
		model.addAttribute("details", orderDetails);
		
		return "shoppingCart";
	}
	
	@RequestMapping(value = "/addOneItem/{id}/**", method = RequestMethod.GET)
	public String addOneItem(@PathVariable String id, HttpServletRequest request, Model model){
		
		int detailId = Integer.parseInt(new AntPathMatcher().extractPathWithinPattern("/{id}/**", request.getRequestURI())); 
		
		for (OrderDetail detail : orderDetails) {
			if(detail.getId() == detailId){
				detail.setQuantity(detail.getQuantity()+1);
				detail.setPrice(detail.getProduct().getPrice() * detail.getQuantity());
			}
		}
		
		model.addAttribute("details", orderDetails);
		
		return "shoppingCart";
	}
	
	@RequestMapping(value = "/substractOneItem/{id}/**", method = RequestMethod.GET)
	public String substractOneItem(@PathVariable String id, HttpServletRequest request, Model model){
		
		int detailId = Integer.parseInt(new AntPathMatcher().extractPathWithinPattern("/{id}/**", request.getRequestURI())); 
		
		for (OrderDetail detail : orderDetails) {
			if(detail.getId() == detailId){
				detail.setQuantity(detail.getQuantity()-1);
				detail.setPrice(detail.getProduct().getPrice() * detail.getQuantity());
				if(detail.getQuantity() <= 0){
					orderDetails.remove(detail);
				}
			}
		}
		
		model.addAttribute("details", orderDetails);
		
		return "shoppingCart";
	}
	
	@RequestMapping(value="/purchase", method=RequestMethod.GET)
	public String purchase(Model model){
		
		Order order = new Order();
		Order newOrder = null;
		
		if(LoginController.getUserLogged() != null){
			
			order.setUser(LoginController.getUserLogged());
			order.setOrderDetails(orderDetails);
			for (OrderDetail orderDetail : orderDetails) {
				order.setTotal(order.getTotal() + orderDetail.getProduct().getPrice() * orderDetail.getQuantity());
				
				newOrder = shoppingCartService.insertOrder(order);
			}
		}else{
			return "login";
		}
		
		model.addAttribute("order", newOrder);
		return "invoise";
	}
	
}

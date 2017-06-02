package cr.ac.ucr.kabekuritechstore.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import cr.ac.ucr.kabekuritechstore.business.ProductService;
import cr.ac.ucr.kabekuritechstore.business.UserService;
import cr.ac.ucr.kabekuritechstore.domain.User;

@Controller
public class LoginController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	ProductService productService;
	
	// variable estatica para el manejo de usuario registrado
	private static User userLogged;

	@RequestMapping(value="/login", method=RequestMethod.GET)
	public String showLogin(Model model){
		model.addAttribute("userLogged", userLogged);
		return "login";
	}
	
	@RequestMapping(value="/logout", method=RequestMethod.GET)
	public String showLogout(Model model){
		// se limpia el usuario
		this.setUserLogged(null);
		//se limpia el carrito
		ShoppingCartController.orderDetails.clear();
		
		return "login";
	}
	
	@RequestMapping(value="/login/userValidation", method = RequestMethod.POST)
	public String userValidation(@RequestParam Map<String, String> requestParams, Model model){
		
		// se obtiene el usuario y la contrasña
		String username = requestParams.get("username");
		String password = requestParams.get("password");
		
		userLogged = userService.userValidation(username, password);
		model.addAttribute("userLogged", userLogged);
		
		if(userLogged != null){						
			if(userLogged.getRoleId().getType().equals("ADMIN")){
				return "login";
			}else{
				model.addAttribute("products", productService.findAll());
				return "catalog";
			}
		}
		return "login";
	}
	
	public static User getUserLogged() {
		return userLogged;
	}

	public static void setUserLogged(User userLogged) {
		LoginController.userLogged = userLogged;
	}
}

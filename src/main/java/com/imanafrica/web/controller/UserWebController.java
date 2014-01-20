package com.imanafrica.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.imanafrica.core.domain.User;
import com.imanafrica.core.services.UserService;

@Controller
@RequestMapping(value="users")
public class UserWebController {
	@Autowired
	UserService userService; 
	
//	@RequestMapping(value="/user/create", method=RequestMethod.GET)
//	public String create(Model model){
//		//Create a dummy customer
//		User user = new User();
//		
//		user.setFirstname("Phumi");
//		user.setLastname("Kubeka");
//		user.setCellnumber(27837930939L);
//		user.setCreateddate(new GregorianCalendar().getTime());
//		user.setEmail("jome.akpoduado@gmail.com");
//		user.setPassword("wordpass15");
//		user.setEnabled(true);
//		user.setUsername("jomski2");
//		
//		try {
//			user = userService.createUser(user);
//			model.addAttribute("message", "User, " + user.getFirstname() +" " + user.getLastname() + " with id: " + user.getId());
//		} catch (InvalidCustomerException e) {
//			model.addAttribute("message", e.getMessage());
//			return "user-create";
//		}
//		
//		return "user-create";
//	}
	
	@RequestMapping(value="create", method=RequestMethod.POST)
	public String customerSubmit(@ModelAttribute User customer, Model model){	
		return null;
	}
	
	@RequestMapping(value="/user/{username}", method=RequestMethod.GET)
	public String getUserByEmail(@PathVariable("username") String username, Model model){
		User user = new User();
		user = userService.findUserByUsername(username).get(0);
		model.addAttribute("user", user);
		
		return "user-result";
		
	}
	
}

package com.SCM.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.SCM.entities.User;
import com.SCM.forms.UserForms;
import com.SCM.helpers.Message;
import com.SCM.helpers.MessageType;
import com.SCM.services.UserServices;

import jakarta.validation.Valid;

@Controller
public class HomeController {
	
	@Autowired
	private UserServices userServices;
	
	// Main Home page view
	@RequestMapping("/")
	public String homeHandler(Model m) {
		
		m.addAttribute("title", "Home - Smart Contact Manager");
		return "home";
	}
	
	// About page view
	@RequestMapping("/about")
	public String aboutHandler(Model m) {
		
		m.addAttribute("title", "About - Smart Contact Manager");
		return "about";
	}
	
	// Services page view
	@RequestMapping("/services")
	public String servicesHandler(Model m) {
		
		m.addAttribute("title", "Services - Smart Contact Manager");
		return "services";
	}
	
	// Contact page view
	@RequestMapping("/contact")
	public String contactHandler(Model m) {
		
		m.addAttribute("title", "Contact - Smart Contact Manager");
		return "contact";
	}
	
	// login page view
	@GetMapping("/login")
	public String loginHandler(Model m) {
		
		m.addAttribute("title", "Login - Smart Contact Manager");
		return "login";
	}
	
	// register page view
	@GetMapping("/register")
	public String registerHandler(Model m) {
		
		m.addAttribute("title", "Register - Smart Contact Manager");
		UserForms userForms = new UserForms();
		m.addAttribute("userForms", userForms);
		return "register";
	}
	
	// Registering the user data in DB
	@PostMapping("/registering_user")
	public String registerUserHandler(@Valid @ModelAttribute UserForms userForms, 
									  BindingResult result,
									  RedirectAttributes redirectAttributes) {
		
		if (result.hasErrors()) {
			return "register";
		}
		
		User user = new User();
		user.setName(userForms.getName());
		user.setEmail(userForms.getEmail());
		user.setPassword(userForms.getPassword());
		user.setAbout(userForms.getAbout());
		user.setPhoneNumber(userForms.getPhoneNumber());
		user.setProfilePic("none");
		user.setEnabled(false);
		
		userServices.save(user);
		
		redirectAttributes.addFlashAttribute("message", new Message("Successfully Registered...", MessageType.green));
		
	
		return "redirect:/register";
	}
}

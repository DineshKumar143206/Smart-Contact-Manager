package com.SCM.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.SCM.entities.Contact;
import com.SCM.entities.User;
import com.SCM.helpers.FindingLoggedInUserEmail;
import com.SCM.services.ContactServices;
import com.SCM.services.UserServices;

@ControllerAdvice
public class BaseController {

	@Autowired
	private UserServices userServices;
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
		// Logged in user details of name and email can be accessed by any page
		@ModelAttribute
		public void addLoggedInUserNameAndEmail(Model m, Authentication authentication) {
			
			if (authentication == null) {
				return;
			}
			
			String username = FindingLoggedInUserEmail.getEmailOfLoggedUser(authentication);
			
			logger.info("User logged in: {}", username);
			
			User user = userServices.getUserByEmail(username);
	
			System.out.println(user.toString());
			System.out.println(user.getName());
			System.out.println(user.getEmail());
			
			m.addAttribute("loggedinuser",user);
			}
}

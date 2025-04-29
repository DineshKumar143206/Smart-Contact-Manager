package com.SCM.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.SCM.entities.User;
import com.SCM.helpers.Message;
import com.SCM.helpers.MessageType;
import com.SCM.repository.UserRepository;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/auth")
public class EmailController {

	@Autowired
	private UserRepository userRepository;
	
	@GetMapping("/verify-email")
	public String verifyingEmail(@RequestParam("token") String token, HttpSession httpSession) {
		
		User user = userRepository.findByEmailToken(token).orElse(null);
		
		if (user != null) {
			
			if (user.getEmailToken().equals(token)) {
				user.setEmailVerified(true);
				user.setEnabled(true);
				userRepository.save(user);
				httpSession.setAttribute("message", new Message("Email  verified, you can login", MessageType.green));
				return "email_success";
			}
			httpSession.setAttribute("message", new Message("Email not verified, token is invalid", MessageType.red));
			return "email_error";
		}
		httpSession.setAttribute("message", new Message("Email not verified, token is invalid", MessageType.red));
		
		return "email_error";
	}
}

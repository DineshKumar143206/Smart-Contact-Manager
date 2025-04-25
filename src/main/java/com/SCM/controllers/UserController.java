package com.SCM.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {
	
	// User Dashboard
	@RequestMapping("/dashboard")
	public String userDashboardHandler() {
		
		return "user/dashboard";
	}
	
	// User Profile
	@GetMapping("/profile")
	public String userProfileHandler() {
		
		
		return "user/profile";
	}

	@GetMapping("/feedback")
	public String feedbackHandler() {
		
		return "user/feedback";
	}
}

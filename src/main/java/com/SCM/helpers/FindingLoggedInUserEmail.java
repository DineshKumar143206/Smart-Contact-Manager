package com.SCM.helpers;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;


public class FindingLoggedInUserEmail {

	public static String getEmailOfLoggedUser(Authentication authentication) {
		
		if (authentication instanceof OAuth2AuthenticationToken) {
			
		 var aOAuth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;
		 var clientid = aOAuth2AuthenticationToken.getAuthorizedClientRegistrationId();
		 
		 var oauth2user = (OAuth2User)authentication.getPrincipal();
		 
		 String username = "";

			// If Google user Logged in
		if (clientid.equalsIgnoreCase("google")) {
			 
			System.out.println("Google user logged in");
			
			username = oauth2user.getAttribute("email").toString();
		}

		// If Github user Logged in
		else if (clientid.equalsIgnoreCase("github")) {
			 
			System.out.println("Github user logged in");
			
			username = oauth2user.getAttribute("email") != null ? 
					 oauth2user.getAttribute("email").toString()
					 : oauth2user.getAttribute("login").toString()+"@gmail.com";
			
		}

		return username;
		}
		// If Self Logged in user
		else {
			System.out.println("Database user logged in");
			return authentication.getName();
		}
	}
}

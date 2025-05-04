package com.SCM.configurations;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.SCM.entities.Providers;
import com.SCM.entities.User;
import com.SCM.helpers.AppConstants;
import com.SCM.repository.UserRepository;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class OauthAuthenticationSuccessHandler implements AuthenticationSuccessHandler{

	Logger logger = LoggerFactory.getLogger(OauthAuthenticationSuccessHandler.class);
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request,
										HttpServletResponse response,
										Authentication authentication) throws IOException, ServletException {
	
		
		 // identify the provider of user
		 var OAuth2AuthenticationToken = (OAuth2AuthenticationToken)authentication;
		 String authorizedClientRegistrationId = OAuth2AuthenticationToken.getAuthorizedClientRegistrationId();
		 logger.info(authorizedClientRegistrationId);
		 
		 var oauthUser = (DefaultOAuth2User) authentication.getPrincipal();
		 
		 oauthUser.getAttributes().forEach((key,value) -> {
			 logger.info(key + " : " + value);
		 });
		 
		 User user = new User();
		 user.setUserId(UUID.randomUUID().toString());
		 user.setRoleList(List.of(AppConstants.ROLE_USER));
		 user.setEmailVerified(true);
		 user.setEnabled(true);
		 
		 
		 if (authorizedClientRegistrationId.equalsIgnoreCase("google")) {
			
			 // google attributes to fetch from the user
			 user.setName(oauthUser.getAttribute("name").toString());
			 user.setEmail(oauthUser.getAttribute("email").toString());
			 user.setProfilePic(oauthUser.getAttribute("picture"));
			 user.setProviderUserId(Providers.GOOGLE);
			 user.setProvider(Providers.GOOGLE);
			 user.setPassword("dinuhtbian");
			 user.setAbout("This Account is created by Google");
			 
		}
		 
		 else if (authorizedClientRegistrationId.equalsIgnoreCase("github")) {
			
			 // github attributes to fetch from the user
			 String email = oauthUser.getAttribute("email") != null ? 
					 oauthUser.getAttribute("email").toString()
					 : oauthUser.getAttribute("login").toString()+"@gmail.com";
			 String picture = oauthUser.getAttribute("avatar_url").toString();
			 String name = oauthUser.getAttribute("login").toString();
			 
			 user.setEmail(email);
			 user.setProfilePic(picture);
			 user.setName(name);
			 user.setProviderUserId(Providers.GITHUB);
			 user.setProvider(Providers.GITHUB);
			 user.setPassword("dinuhtbian");
			 user.setAbout("This Account is created by Github");
			 
		}
		 
		 else {
			logger.info("OauthAuthenticationSuccessHandler : Unknown Provider");
		}
		 
		 
			 User user2 = userRepository.findByEmail(user.getEmail()).orElse(null); 
			 if (user2 ==null) { 
				 userRepository.save(user); 
				 
				 logger.info("user saved : " + user.getEmail()); 
				 }
			 
		 
		// redirecting after login
			logger.info("OauthAuthenticationSuccessHandler");
			new DefaultRedirectStrategy().sendRedirect(request, response, "/user/profile");
			
	}

}

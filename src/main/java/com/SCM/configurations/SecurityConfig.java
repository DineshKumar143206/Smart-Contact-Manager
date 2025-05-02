package com.SCM.configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

	@Autowired
	private SecurityCustomUserDetailsService securityCustomUserDetailsService;
	
	@Autowired
	private OauthAuthenticationSuccessHandler oauthAuthenticationSuccessHandler;
	
	@Autowired
	private LoginAuthenticationFailureHandler loginAuthenticationFailureHandler;

	// configuration of authentication provider of spring security
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {

		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		// user detail service object to pass here
		daoAuthenticationProvider.setUserDetailsService(securityCustomUserDetailsService);
		// password encoder object to pass here
		daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());

		return daoAuthenticationProvider;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

		// urls configuration for public and private access
		httpSecurity.authorizeHttpRequests(authorize -> {
			authorize.requestMatchers("/user/**").authenticated();
			authorize.anyRequest().permitAll();
		});
		
		// login configurations
		  httpSecurity.formLogin(formLogin -> {
		  
		  formLogin.loginPage("/login"); 
		  formLogin.loginProcessingUrl("/auth");
		  formLogin.defaultSuccessUrl("/user/profile");
		  formLogin.usernameParameter("email");
		  formLogin.passwordParameter("password");
		  
		  formLogin.failureHandler(loginAuthenticationFailureHandler);
		  
		  });
		 
		  // logout configurations
		  httpSecurity.csrf(AbstractHttpConfigurer::disable);
		  httpSecurity.logout(logoutForm -> {
		  
		  logoutForm.logoutUrl("/logouting");
		  logoutForm.logoutSuccessUrl("/login?logout=true"); 
		  
		  });
		  
		  //oauth configurations 
		  httpSecurity.oauth2Login(oauth -> {
			  
			  oauth.loginPage("/login");
			  oauth.successHandler(oauthAuthenticationSuccessHandler);
			  
		  });
		return httpSecurity.build();
	}

}

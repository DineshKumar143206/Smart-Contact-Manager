package com.SCM.helpers;

import org.springframework.security.core.Authentication;

public class EmailToken {

		
		public static String getLinkForEmailVerification(String emailToken) {
			
			String link = "http://localhost:8080/auth/verify-email?token="+emailToken;
			
			return link;
		}
	
}

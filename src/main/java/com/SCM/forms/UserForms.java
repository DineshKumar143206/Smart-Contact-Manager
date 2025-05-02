package com.SCM.forms;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserForms {

	@Size(min = 3, message = "Username is Required with Minimum 3 Characters !")
	private String name;
	@Email(regexp = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$", message = "Email Must be a Well-Formed Email Address !")
	private String email;
	@Pattern(regexp = "^\\S{8,}$",message = "Password must be at least 8 characters long with no spaces.")
	private String password;
	@Size(min = 10, max = 12, message = "Invalid Phone Number")
	private String phoneNumber;
	@NotBlank(message = "Something About Yourself Please !")
	private String about;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getAbout() {
		return about;
	}
	public void setAbout(String about) {
		this.about = about;
	}
	public UserForms(String name, String email, String password, String phoneNumber, String about) {
		super();
		this.name = name;
		this.email = email;
		this.password = password;
		this.phoneNumber = phoneNumber;
		this.about = about;
	}
	public UserForms() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}

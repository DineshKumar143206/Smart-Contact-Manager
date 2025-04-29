package com.SCM.forms;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class ContactForms {

	@NotBlank(message = "Name is required")
	private String name;
	@NotBlank(message = "Email id is required")
	@Email(regexp = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$", message = "Email Must be a Well-Formed Email Address !")
	private String email;
	@NotBlank(message = "Phone Number is required")
	@Pattern(regexp = "^[0-9]{10}$", message = "Phone number must be exactly 10 digits.")
	private String phoneNumber;
	@NotBlank(message = "Address is required")
	private String address;
	private String description;
	private boolean favorite ;
	private String websiteLink;
	private String linkedinLink;
	
	private MultipartFile picture;
	
	private String pic;

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

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isFavorite() {
		return favorite;
	}

	public void setFavorite(boolean favorite) {
		this.favorite = favorite;
	}

	public String getWebsiteLink() {
		return websiteLink;
	}

	public void setWebsiteLink(String websiteLink) {
		this.websiteLink = websiteLink;
	}

	public String getLinkedinLink() {
		return linkedinLink;
	}

	public void setLinkedinLink(String linkedinLink) {
		this.linkedinLink = linkedinLink;
	}

	public MultipartFile getPicture() {
		return picture;
	}

	public void setPicture(MultipartFile picture) {
		this.picture = picture;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public ContactForms(String name, String email, String phoneNumber, String address, String description,
			boolean favorite, String websiteLink, String linkedinLink, MultipartFile picture, String pic) {
		super();
		this.name = name;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.address = address;
		this.description = description;
		this.favorite = favorite;
		this.websiteLink = websiteLink;
		this.linkedinLink = linkedinLink;
		this.picture = picture;
		this.pic  = pic;
	}

	public ContactForms() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}

package com.SCM.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Contact {
	
	@Id
	private String id;
	private String name;
	private String email;
	private String phoneNumber;
	private String address;
	private String picture;
	@Column(length = 1000)
	private String description;
	private boolean favorite = false;
	private String websiteLink;
	private String linkedinLink;
	
	private String cloudinaryPicturePublicId;
	
	@ManyToOne
	@JsonIgnore
	private User user;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

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

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getCloudinaryPicturePublicId() {
		return cloudinaryPicturePublicId;
	}

	public void setCloudinaryPicturePublicId(String cloudinaryPicturePublicId) {
		this.cloudinaryPicturePublicId = cloudinaryPicturePublicId;
	}

	public Contact(String id, String name, String email, String phoneNumber, String address, String picture,
			String description, boolean favorite, String websiteLink, String linkedinLink, User user,
			 String cloudinaryPicturePublicId) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.address = address;
		this.picture = picture;
		this.description = description;
		this.favorite = favorite;
		this.websiteLink = websiteLink;
		this.linkedinLink = linkedinLink;
		this.user = user;
		this.cloudinaryPicturePublicId = cloudinaryPicturePublicId;
	}

	public Contact() {
		super();
		// TODO Auto-generated constructor stub
	}

	
}

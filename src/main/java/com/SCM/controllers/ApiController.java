package com.SCM.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.SCM.entities.Contact;
import com.SCM.services.ContactServices;

@RestController
@RequestMapping("/api")
public class ApiController {
	
	@Autowired
	private ContactServices contactServices;

	@GetMapping("/contacts/{id}")
	public Contact getContact(@PathVariable String id) {
		
		return contactServices.getContactByContactId(id);
	}
}

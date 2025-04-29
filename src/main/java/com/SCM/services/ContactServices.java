package com.SCM.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.SCM.entities.Contact;
import com.SCM.entities.User;

public interface ContactServices {

	// Save contacts
	Contact saveContact(Contact contact);
	
	// Update contacts
	Contact updateContact(Contact contact);
	
	// Get all contacts
	List<Contact> getAllContacts();
	
	// Get contact by contact id
	Contact getContactByContactId(String id);
	
	// Delete contact by id
	void deleteContactById(String id);
	
	// Get contacts by user id
	List<Contact> getContactsByUserId(String userId);
	
	// Pagination 
	Page<Contact> getByUser(User user, int page, int size, String sortBy, String sortDirection);
	
	// Search contact by name
	Page<Contact> searchContactByName(String nameKeyword, int size, int page, String sortBy, String order, User user);
	
	// Search contact by email
	Page<Contact> searchContactByEmail(String emailKeyword, int size, int page, String sortBy, String order, User user);
		
	// Search contact by phoneNumber
	Page<Contact> searchContactByPhoneNumber(String phoneNumberKeyword, int size, int page, String sortBy, String order, User user);

}

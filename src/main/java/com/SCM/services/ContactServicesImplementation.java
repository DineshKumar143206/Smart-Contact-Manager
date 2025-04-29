package com.SCM.services;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.SCM.entities.Contact;
import com.SCM.entities.User;
import com.SCM.helpers.ResourceNotFoundException;
import com.SCM.repository.ContactRepository;


@Service
public class ContactServicesImplementation implements ContactServices{

	@Autowired
	private ContactRepository contactRepository;
	
	@Override
	public Contact saveContact(Contact contact) {
		
		String contactId = UUID.randomUUID().toString();
		contact.setId(contactId);
		
		Contact save = contactRepository.save(contact);
		return save;
	}

	@Override
	public Contact updateContact(Contact contact) {
		
		Contact contactOld = contactRepository.findById(contact.getId()).orElseThrow(() -> new ResourceNotFoundException("Contact not found"));
		contactOld.setName(contact.getName());
		contactOld.setEmail(contact.getEmail());
		contactOld.setAddress(contact.getAddress());
		contactOld.setDescription(contact.getDescription());
		contactOld.setFavorite(contact.isFavorite());
		contactOld.setPhoneNumber(contact.getPhoneNumber());
		contactOld.setWebsiteLink(contact.getWebsiteLink());
		contactOld.setLinkedinLink(contact.getLinkedinLink());
		contactOld.setPicture(contact.getPicture());
		
		Contact contactNew = contactRepository.save(contactOld);
		
		return contactNew;
	}

	@Override
	public List<Contact> getAllContacts() {
		List<Contact> allcontacts = contactRepository.findAll();
		return allcontacts;
	}

	@Override
	public Contact getContactByContactId(String id) {
		
		return  contactRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("contact not found of :" + id));
	}

	@Override
	public void deleteContactById(String id) {
		
		contactRepository.deleteById(id);
	}

	@Override
	public List<Contact> getContactsByUserId(String userId) {
		
		List<Contact> contactByUserId = contactRepository.findByUserId(userId);
		return contactByUserId;
	}

	@Override
	public Page<Contact> getByUser(User user, int page, int size, String sortBy, String direction) {
		
		Sort sort = direction.equals("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
		
		var pageable = PageRequest.of(page, size, sort);
		
		return contactRepository.findByUser(user, pageable);
	}

	@Override
	public Page<Contact> searchContactByName(String nameKeyword, int size, int page, String sortBy, String order, User user) {
		
		Sort sort = order.equals("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending(); 
		var pageable=PageRequest.of(page, size, sort);
		return contactRepository.findByUserAndNameContaining(user, nameKeyword, pageable);
	}

	@Override
	public Page<Contact> searchContactByEmail(String emailKeyword, int size, int page, String sortBy, String order, User user) {
		
		Sort sort = order.equals("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending(); 
		var pageable=PageRequest.of(page, size, sort);
		return contactRepository.findByUserAndEmailContaining(user, emailKeyword, pageable);
	}

	@Override
	public Page<Contact> searchContactByPhoneNumber(String phoneNumberKeyword, int size, int page, String sortBy, String order, User user) {
		
		Sort sort = order.equals("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending(); 
		var pageable=PageRequest.of(page, size, sort);
		return contactRepository.findByUserAndPhoneNumberContaining(user, phoneNumberKeyword, pageable);
	}

	
	
}

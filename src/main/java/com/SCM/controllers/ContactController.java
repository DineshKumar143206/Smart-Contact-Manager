package com.SCM.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.SCM.entities.Contact;
import com.SCM.entities.User;
import com.SCM.forms.ContactForms;
import com.SCM.forms.SearchForms;
import com.SCM.helpers.FindingLoggedInUserEmail;
import com.SCM.helpers.Message;
import com.SCM.helpers.MessageType;
import com.SCM.services.ContactServices;
import com.SCM.services.PictureService;
import com.SCM.services.UserServices;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/user/contacts")
public class ContactController {
	
	@Autowired
	private ContactServices contactServices;
	
	@Autowired
	private UserServices userServices;
	
	@Autowired
	private PictureService pictureService;

	@RequestMapping("/add")
	public String addContact(Model m) {
		
		ContactForms contactForms = new ContactForms();
		
		m.addAttribute("contactForms", contactForms);
		return "user/add_contact";
	}
	
	// adding a contact 
	@PostMapping("/adding_contact")
	public String addingContactHandler (@Valid @ModelAttribute ContactForms contactForms,
										BindingResult result,
										RedirectAttributes redirectAttributes,
										Authentication authentication) {
		
		if (result.hasErrors()) {
			redirectAttributes.addFlashAttribute("message", new Message("Please correct the mistakes...", MessageType.red));
			return "user/add_contact";
		}
		
		String username = FindingLoggedInUserEmail.getEmailOfLoggedUser(authentication);
		User userByEmail = userServices.getUserByEmail(username);
		
		Contact contact = new Contact();
		contact.setName(contactForms.getName());
		contact.setEmail(contactForms.getEmail());
		contact.setAddress(contactForms.getAddress());
		contact.setPhoneNumber(contactForms.getPhoneNumber());
		contact.setDescription(contactForms.getDescription());
		contact.setFavorite(contactForms.isFavorite());
		contact.setWebsiteLink(contactForms.getWebsiteLink());
		contact.setLinkedinLink(contactForms.getLinkedinLink());
		contact.setUser(userByEmail);
		
		if (contactForms.getPicture() != null && !contactForms.getPicture().isEmpty()) {
			
			// picture uploading 
			String filename = UUID.randomUUID().toString();
			String fileURL =  pictureService.uploadPicture(contactForms.getPicture(), filename);
			
			contact.setPicture(fileURL);
			contact.setCloudinaryPicturePublicId(filename);
			
		}
		
		contactServices.saveContact(contact);
		redirectAttributes.addFlashAttribute("message", new Message("You have Successfully Added a Contact", MessageType.green));
		
		return "redirect:/user/contacts/add";
	
		}
		
		// viewing all Contacts after user added contacts
		@RequestMapping
		public String viewContacts(@RequestParam (value = "page", defaultValue = "0") Integer page,
								   @RequestParam (value = "size", defaultValue = "5") Integer size,
								   @RequestParam (value = "sortBy", defaultValue = "name") String sortBy,
								   @RequestParam (value = "direction", defaultValue = "asc") String direction,
								   Model m,
								   Authentication authentication) {
			
			String emailOfLoggedUser = FindingLoggedInUserEmail.getEmailOfLoggedUser(authentication);
			
			User user = userServices.getUserByEmail(emailOfLoggedUser);
			
			Page<Contact> pageContact = contactServices.getByUser(user, page, size, sortBy, direction);
			
			m.addAttribute("pageContact",pageContact);
			m.addAttribute("pageSize" , 5);
			m.addAttribute("searchForms", new SearchForms());
			
			return "user/contacts";
		}
	
		// searching for a contact in contacts
	
		@RequestMapping("/search")
		public String searchHandler(@ModelAttribute SearchForms searchForms,
									@RequestParam(value = "size", defaultValue = "5") int size,
									@RequestParam(value = "page", defaultValue = "0") int page,
									@RequestParam(value = "sortBy", defaultValue = "name") String sortBy,
									@RequestParam(value = "direction", defaultValue = "asc") String direction,
									Model m,
									Authentication authentication) {
			
		System.out.println(searchForms.getField() + " : "+ searchForms.getValue());
		
		var user = userServices.getUserByEmail(FindingLoggedInUserEmail.getEmailOfLoggedUser(authentication));
		
		
		Page<Contact> pageContact = null;
		switch (searchForms.getField().toLowerCase()) {
        case "name":
            pageContact = contactServices.searchContactByName(searchForms.getValue(), size, page, sortBy, direction, user);
            break;
        case "email":
            pageContact = contactServices.searchContactByEmail(searchForms.getValue(), size, page, sortBy, direction, user);
            break;
        case "phone":
            pageContact = contactServices.searchContactByPhoneNumber(searchForms.getValue(), size, page, sortBy, direction, user);
            break;
        default:
        	
            break;
		}
		
		System.out.println("pageContact" + pageContact);
		
		m.addAttribute("searchForms", searchForms);
		m.addAttribute("pageContact", pageContact);
		m.addAttribute("pageSize" , 5);
		
		return "user/search";
		}
		
		// deleting a contact using contact id in contacts and search
		
		@RequestMapping("/delete/{id}")
		public String deletingContact(@PathVariable String id,
									  RedirectAttributes redirectAttributes) {
			
			contactServices.deleteContactById(id);
			
			redirectAttributes.addFlashAttribute("message", new Message("You have Successfully deleted a Contact", MessageType.red));
			
			return "redirect:/user/contacts";
		}
		
		// updating a contact using contact id in contacts and search
		
		@GetMapping("/updating_contact/{id}")
		public String updatingContact(@PathVariable String id, Model m) {
			
			Contact contact = contactServices.getContactByContactId(id);
			
			ContactForms contactForms = new ContactForms();
			contactForms.setName(contact.getName());
			contactForms.setEmail(contact.getEmail());
			contactForms.setPhoneNumber(contact.getPhoneNumber());
			contactForms.setAddress(contact.getAddress());
			contactForms.setDescription(contact.getDescription());
			contactForms.setFavorite(contact.isFavorite());
			contactForms.setWebsiteLink(contact.getWebsiteLink());
			contactForms.setLinkedinLink(contact.getLinkedinLink());
			contactForms.setPic(contact.getPicture());
			
			m.addAttribute("contactForms", contactForms);
			m.addAttribute("contactId", id);
			
			return "/user/updating_contact";
		}
		
		// fetching updated contact information
		@PostMapping("/updated_contact/{id}")
		public String updatedContact(@PathVariable String id,@Valid 
									 @ModelAttribute ContactForms contactForms,
									 BindingResult result,
									 RedirectAttributes redirectAttributes
									 ) {
			if (result.hasErrors()) {
				return "/user/updating_contact";
			}
			
			Contact contact = contactServices.getContactByContactId(id);
			contact.setId(id);
			contact.setName(contactForms.getName());
			contact.setEmail(contactForms.getEmail());
			contact.setAddress(contactForms.getAddress());
			contact.setDescription(contactForms.getDescription());
			contact.setFavorite(contactForms.isFavorite());
			contact.setPhoneNumber(contactForms.getPhoneNumber());
			contact.setWebsiteLink(contactForms.getWebsiteLink());
			contact.setLinkedinLink(contactForms.getLinkedinLink());
			
			if (contactForms.getPicture() != null && !contactForms.getPicture().isEmpty() ) {
				String dp = UUID.randomUUID().toString();
				String pictureUrl = pictureService.uploadPicture(contactForms.getPicture(), dp);
				contact.setCloudinaryPicturePublicId(dp);
				contact.setPicture(pictureUrl);
				contactForms.setPic(pictureUrl);
				
			}
			
			Contact updatedContact = contactServices.updateContact(contact);
			System.out.println("contact updated" + updatedContact);
			redirectAttributes.addFlashAttribute("message", new Message("You have Successfully updated a Contact", MessageType.green));
			
			return "redirect:/user/contacts";
		}
		
}

package com.SCM.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.SCM.entities.Contact;
import com.SCM.entities.User;

@Repository
public interface ContactRepository extends JpaRepository<Contact, String>{

	// pagination
	Page<Contact> findByUser(User user, Pageable pageable);
	
	// searching contact by user id
	@Query("SELECT c FROM Contact c WHERE c.user.id = : userId")
	List<Contact> findByUserId(@Param("userId") String userId);
	
	// searching contact using name, email and phoneNumber
	Page<Contact> findByUserAndNameContaining(User user, String namekeyword, Pageable pageable);
	
	Page<Contact> findByUserAndEmailContaining(User user, String emailkeyword, Pageable pageable);
	
	Page<Contact> findByUserAndPhoneNumberContaining(User user, String phoneNumberkeyword, Pageable pageable);
	
}

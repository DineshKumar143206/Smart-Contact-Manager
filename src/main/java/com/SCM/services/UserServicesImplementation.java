package com.SCM.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.ExampleMatcher.PropertyValueTransformer;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.SCM.entities.User;
import com.SCM.helpers.AppConstants;
import com.SCM.helpers.EmailToken;
import com.SCM.helpers.ResourceNotFoundException;
import com.SCM.repository.UserRepository;

@Service
public class UserServicesImplementation implements UserServices{

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private EmailService emailService;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public User save(User user) {
		
		// user id generating
		String userId = UUID.randomUUID().toString();
		user.setUserId(userId);
		
		// password encode
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		
		// set the role
		user.setRoleList(List.of(AppConstants.ROLE_USER));
		
		logger.info(user.getProvider().toString());
		
		String emailToken = UUID.randomUUID().toString();
		user.setEmailToken(emailToken);
		
		User savedUser =  userRepository.save(user);
		
		String emailLink = EmailToken.getLinkForEmailVerification(emailToken);
		emailService.sendEmail(savedUser.getEmail(), "Verify Account : Smart Contact Manager by Dinesh Kumar", emailLink);
		
		
		return savedUser;
	}

	@Override
	public Optional<User> getById(String id) {
		Optional<User> userbyid = userRepository.findById(id);
		return userbyid;
	}

	@Override
	public Optional<User> updateUser(User user) {
	User user2 = userRepository.findById(user.getUserId()).orElseThrow(()-> 
	new ResourceNotFoundException("User not found"));
	
	user2.setName(user.getName());
	user2.setEmail(user.getEmail());
	user2.setPassword(user.getPassword());
	user2.setAbout(user.getAbout());
	user2.setPhoneNumber(user.getPhoneNumber());
	user2.setProfilePic(user.getProfilePic());
	user2.setEnabled(user.isEnabled());
	user2.setEmailVerified(user.isEmailVerified());
	user2.setPhoneVerified(user.isPhoneVerified());
	user2.setProvider(user.getProvider());
	user2.setProviderUserId(user.getProviderUserId());
	
	User saveUser =  userRepository.save(user2);
	return Optional.ofNullable(saveUser);
	}

	@Override
	public void deleteUser(String id) {
	
		User user2 = userRepository.findById(id).orElseThrow(()-> 
		new ResourceNotFoundException("User not found"));
		
		userRepository.delete(user2);

	}

	@Override
	public boolean isUserExist(String id) {
		
		User user2 = userRepository.findById(id).orElse(null);
		if(user2 != null) {
			return true;
		}
		
		return false;
	}

	@Override
	public boolean isUserExistByEmail(String Email) {
		
		User user = userRepository.findByEmail(Email).orElse(null);
		if(user != null) {
			return true;
		}
		return false;
	}

	@Override
	public List<User> getAllUsers() {
		
		List<User> user =  userRepository.findAll();
		return user;
	}

	@Override
	public User getUserByEmail(String email) {
		
		User user = userRepository.findByEmail(email).orElse(null);
		return user;
	}

}

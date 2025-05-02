package com.SCM.services;

import java.util.List;
import java.util.Optional;

import com.SCM.entities.User;

public interface UserServices {
	
	User save(User user);
	Optional<User> getById(String id);
	Optional<User> updateUser(User user);
	void deleteUser(String id);
	boolean isUserExist(String id);
	boolean isUserExistByEmail(String Email);
	List<User> getAllUsers();
	User getUserByEmail(String email);

}

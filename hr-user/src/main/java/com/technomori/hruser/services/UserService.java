package com.technomori.hruser.services;

import java.util.List;

import com.technomori.hruser.entities.User;

public interface UserService {

	User save(User user);

	List<User> findAll();

	User findById(Long id);
	
	User findByEmail(String email);
	
	boolean exists(String email);

}

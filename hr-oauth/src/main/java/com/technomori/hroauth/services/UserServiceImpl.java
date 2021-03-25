package com.technomori.hroauth.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.technomori.hroauth.entities.User;
import com.technomori.hroauth.network.UserServiceProxy;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserServiceProxy userServiceProxy;

	@Override
	public User findByEmail(String email) {
		ResponseEntity<User> response = userServiceProxy.findByEmail(email);
		if(response == null) {
			return null;
		}
		return response.getBody();
	}

	@Override
	public UserDetails loadUserByUsername(String username) {
		User user = findByEmail(username);
		if(user == null) {
			throw new UsernameNotFoundException("Email not found: " + username);
		}
		return user;
	}

}

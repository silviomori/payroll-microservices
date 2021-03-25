package com.technomori.hroauth.services;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.technomori.hroauth.entities.User;

public interface UserService extends UserDetailsService {

	User findByEmail(String email);

}

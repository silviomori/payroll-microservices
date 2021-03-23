package com.technomori.hroauth.services;

import com.technomori.hroauth.entities.User;

public interface UserService {

	User findByEmail(String email);

}

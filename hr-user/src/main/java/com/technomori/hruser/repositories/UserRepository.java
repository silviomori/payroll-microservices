package com.technomori.hruser.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.technomori.hruser.entities.User;
	
public interface UserRepository extends JpaRepository<User, Long> {

	User findByEmail(String email);

}

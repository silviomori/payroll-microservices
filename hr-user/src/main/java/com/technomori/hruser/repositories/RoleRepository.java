package com.technomori.hruser.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.technomori.hruser.entities.Role;
	
public interface RoleRepository extends JpaRepository<Role, Long> {

}

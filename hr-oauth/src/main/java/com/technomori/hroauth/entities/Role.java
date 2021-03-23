package com.technomori.hroauth.entities;

import java.io.Serializable;

public class Role implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;
	private String roleName;
	
	public Role() {
	}

	public Role(String roleName) {
		super();
		this.roleName = roleName;
	}
	
	public Long getId() {
		return id;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

}

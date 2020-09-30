package com.grippaweb.usersmanager.services;

import java.util.List;

import com.grippaweb.usersmanager.entities.Roles;

public interface IRolesService {
	Roles find(long id);
	Roles find(String code);
	List<Roles> listAll();
	boolean save(Roles role);
	boolean remove(long id);
}

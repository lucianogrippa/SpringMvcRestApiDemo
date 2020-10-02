package com.grippaweb.usersmanager.services;

import java.util.List;

import org.springframework.data.domain.Page;

import com.grippaweb.usersmanager.entities.Roles;
import com.grippaweb.usersmanager.entities.User;

public interface IUserService {
	User authUser(String username, String pwd, String appKey);

	User find(long id);

	User authUserWithUUID(String username, long userId);

	boolean save(User user);

	boolean delete(long userId);
	
	List<User> listAll();
	
	List<User> listByRole(Roles role);
	
	User findByUsername(String username);
	
	User findByToken(String token);
	
	Page<User> listAll(String textSearch, Integer indexRow, Integer maxRow);
}

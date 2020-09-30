package com.grippaweb.usersmanager.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.grippaweb.usersmanager.entities.Roles;
import com.grippaweb.usersmanager.entities.User;


public interface UserRepository extends JpaRepository<User,Long> {
	
    	@Query("Select u from User u where u.username = :uid and u.secret = :sec")
	public User findByCredential(@Param("sec") String username, @Param("uid") String pwd);
    	
    	@Query("Select u from User u where u.username = :name and u.userId = :uid")
	public User findByIdUsername(@Param("name") String username,@Param("uid") long id);
    	
	public User findByUsername(String username);
	
	public List<User> findByRoles(Roles role);

}

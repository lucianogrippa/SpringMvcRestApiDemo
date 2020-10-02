package com.grippaweb.usersmanager.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.grippaweb.usersmanager.entities.Roles;
import com.grippaweb.usersmanager.entities.User;


public interface UserRepository extends PagingAndSortingRepository<User, Long> {
	
    	@Query("Select u from User u where u.username = :uid and u.secret = :sec")
	public User findByCredential(@Param("sec") String username, @Param("uid") String pwd);
    	
    	@Query("Select u from User u where u.username = :name and u.userId = :uid")
	public User findByIdUsername(@Param("name") String username,@Param("uid") long id);
    	
	public User findByUsername(String username);
	
	public List<User> findByRoles(Roles role);

	@Query("Select u from User u where u.username like  CONCAT('%',:search,'%') OR u.firstname like CONCAT('%',:search,'%') OR lastname like CONCAT('%',:search,'%') ")
	public Page<User> findAllFilter(@Param("search") String searchText,Pageable pageable);

}

/**
 * 
 */
package com.grippaweb.usersmanager.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.grippaweb.usersmanager.entities.Roles;


public interface RolesRepository extends JpaRepository<Roles,Long> {

    	@Query("from Roles r where r.code = :code")
	public Roles findByCode(@Param(value = "code") String code);

    	@Query("from Roles r order by r.name")
	public List<Roles> listAll();
}

/**
 * 
 */
package com.grippaweb.usersmanager.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.grippaweb.usersmanager.entities.Roles;


public interface RolesRepository extends JpaRepository<Roles,Long> {

	public Optional<Roles> findByCode(String code);
}

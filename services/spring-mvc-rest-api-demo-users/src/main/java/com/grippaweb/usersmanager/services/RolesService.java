/**
 * 
 */
package com.grippaweb.usersmanager.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.grippaweb.usersmanager.entities.Roles;
import com.grippaweb.usersmanager.helpers.LogHelper;
import com.grippaweb.usersmanager.repositories.RolesRepository;

@Service(value = "rolesService")
@Transactional(readOnly = true)
@CacheConfig(cacheNames = "roleservice")
public class RolesService implements IRolesService {

    @Autowired
    RolesRepository repository;

    LogHelper logger = new LogHelper(getClass());

    @Override
    @Caching
    public Roles find(long id) {
	Roles role = null;
	Optional<Roles> opt = repository.findById(id);
	if (opt.isPresent()) {
	    role = opt.get();
	}
	return role;
    }

    @Override
    @Caching
    public Roles find(String code) {
	Roles role = null;
	Optional<Roles> opt = repository.findByCode(code);
	if (opt.isPresent()) {
	    role = opt.get();
	}
	return role;
    }

    @Override
    @Caching
    public List<Roles> listAll() {
	return repository.findAll();
    }

    @Override
    @Caching(evict = { @CacheEvict(cacheNames = "roleservice", allEntries = true) })
    @Transactional
    public boolean save(Roles role) {
	if (role != null) {
	    Roles r = repository.save(role);
	    long roleId = r.getId();
	    return roleId > 0;
	}
	
	return false;
    }

    @Override
    @Caching(evict = { @CacheEvict(cacheNames = "roleservice", allEntries = true) })
    @Transactional
    public boolean remove(long id) {
	boolean retVal = false;
	try {
	    repository.deleteById(id);
	    Optional<Roles> role = repository.findById(id);
	    retVal = !role.isPresent() || role.isEmpty();
	    
	} catch (Exception e) {
	    logger.logException(e);
	}
	return retVal;
    }

}

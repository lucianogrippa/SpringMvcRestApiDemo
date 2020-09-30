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
	if (opt != null) {
	    role = opt.get();
	}
	return role;
    }

    @Override
    @Caching
    public Roles find(String code) {
	return repository.findByCode(code);
    }

    @Override
    @Caching
    public List<Roles> listAll() {
	return repository.listAll();
    }

    @Override
    @Caching(evict = { @CacheEvict(cacheNames = "roleservice", allEntries = true) })
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
    public boolean remove(long id) {
	boolean retVal = false;
	try {
	    repository.deleteById(id);
	    retVal = true;
	} catch (Exception e) {
	    logger.logException(e);
	}
	return retVal;
    }

}

package com.grippaweb.usersmanager.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.grippaweb.usersmanager.entities.Roles;
import com.grippaweb.usersmanager.entities.User;
import com.grippaweb.usersmanager.helpers.LogHelper;
import com.grippaweb.usersmanager.repositories.UserRepository;


@Service(value = "userService")
@Transactional(readOnly = true)
@CacheConfig(cacheNames = "userservice")
public class UserService implements IUserService {

	@Value("${application.key}")
	String apiKey;

	@Autowired
	UserRepository repository;

	LogHelper logger = new LogHelper(getClass());

	@Override
	@Cacheable
	public List<User> listByRole(Roles role) {
	    return repository.findByRoles(role);
	}
	
	@Override
	@Cacheable
	public User authUser(String username, String pwd, String appKey) {
		logger.logDebug("in authUser: " + username + " pwd: " + pwd + " appKey: " + appKey);
		if (!StringUtils.isEmpty(appKey) && appKey.equals(apiKey)) {
			logger.logDebug("appKey: " + appKey + " ok ");
			User dbUser = repository.findByCredential(username, pwd);
			if (dbUser != null) {
				return dbUser;
			}
		}
		return null;
	}

	@Override
	@Cacheable
	public User find(long id) {
		logger.logDebug("in find id: " + id);
		Optional<User> dbUser = repository.findById(id);
		if (dbUser != null) {
			return dbUser.get();
		}
		return null;
	}

	@Override
	@Cacheable
	public User authUserWithUUID(String username, long userId) {
		return repository.findByIdUsername(username, userId);
	}

	@Override
	@Cacheable
	public User authUserByRequestAuthToken(String authToken) {

		return null;
	}
	
	@Override
	@Cacheable
	public List<User> listAll() {
		return repository.findAll();
	}
	
	@Override
	@Transactional
	@Caching(evict = { 
		@CacheEvict(cacheNames="userservice", allEntries = true)
	})
	public boolean save(User user) {
		boolean isSaved = false;
		if (user != null && !StringUtils.isEmpty(user.getUsername())) {
			User saved = repository.save(user);
			isSaved = saved != null && saved.getUserId()>0;
		}
		return isSaved;
	}

	@Override
	@Transactional
	@Caching(evict = { 
		@CacheEvict(cacheNames="userservice", allEntries = true)
	})
	public boolean delete(long userId) {
		boolean isSaved = false;
		repository.deleteById(userId);
		return isSaved;
	}
}

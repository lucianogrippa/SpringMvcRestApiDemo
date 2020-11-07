package com.grippaweb.jwt.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.grippaweb.jwt.dtos.User;
import com.grippaweb.jwt.helpers.LogHelper;
import com.grippaweb.jwt.services.UserService;

@Service("customUserDetailsService")
public class CustomUserDetailsService implements UserDetailsService {
    LogHelper logger = new LogHelper(getClass());

    @Autowired
    UserService userService;

    @Override
    public UserDetails loadUserByUsername(String uid) throws UsernameNotFoundException {
	String message = "";
	try {
	    if (StringUtils.isEmpty(uid) || uid.length() < 3) {
		message = "Invalid username or password";

		logger.logDebug(message);

		throw new UsernameNotFoundException(message);
	    }

	    User user = userService.loadUser(uid);

	    if (user == null) {
		message = String.format("user %s is not found!!", uid);

		logger.logDebug(message);

		throw new UsernameNotFoundException(message);
	    }

	    UserBuilder builder = null;
	    builder = org.springframework.security.core.userdetails.User.withUsername(user.getUsername());
	    builder.disabled((user.isActive()));
	    builder.password(user.getSecret());

	    String[] profiles = user.getRoles().stream().map(a -> a.getCode()).toArray(String[]::new);

	    builder.authorities(profiles);

	    return builder.build();
	}
	catch(UsernameNotFoundException e) {
	    logger.logException(e);
	    
	    throw new UsernameNotFoundException(message);
	}
	catch (Exception e) {
	    logger.logException(e);
	    throw new UsernameNotFoundException(message);
	}
    }
}
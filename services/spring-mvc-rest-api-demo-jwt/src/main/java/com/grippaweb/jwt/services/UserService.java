package com.grippaweb.jwt.services;

import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.grippaweb.jwt.configuration.UserServiceConfig;
import com.grippaweb.jwt.dtos.User;
import com.grippaweb.jwt.helpers.LogHelper;

@Service("userService")
public class UserService implements IUserService {

    @Autowired
    UserServiceConfig userConfig;

    LogHelper logger = new LogHelper(getClass());

    @Override
    public User loadUser(String uid) throws URISyntaxException {
	URI url = null;
	logger.logInfo("searching for " + uid);
	User user = null;

	String entryPoint = String.format("%s%s/%s", userConfig.getSrvUrl(), userConfig.getServicesFindUid(), uid);
	url = new URI(entryPoint);
	logger.logDebug("setup entripoint: " + entryPoint);

	RestTemplate restTemplate = new RestTemplate();
	restTemplate.getInterceptors()
		.add(new BasicAuthenticationInterceptor(userConfig.getUserUserId(), userConfig.getUserPassword()));
	logger.logDebug("setup entripoint: " + entryPoint + " credentials");
	user = restTemplate.getForObject(url, User.class);

	if (user != null) {
	    logger.logDebug("entrypoint response: " + user.getUsername());
	}
	return user;
    }

}

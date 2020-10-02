package com.grippaweb.jwt.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component 
@ConfigurationProperties("userservice")
@Data
public class UserServiceConfig 
{
	private String srvUrl;
	private String servicesFindId;
	private String servicesFindUid;
	private String userUserId;
	private String userPassword;
}
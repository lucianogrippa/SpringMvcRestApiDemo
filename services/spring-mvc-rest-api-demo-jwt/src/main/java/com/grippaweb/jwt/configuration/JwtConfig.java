package com.grippaweb.jwt.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@ConfigurationProperties("token-config")
@Data
public class JwtConfig
{
	private String uri;
	private String refresh;
	private String header;
	private String prefix;
	private int expiration;
	private String secret;
}

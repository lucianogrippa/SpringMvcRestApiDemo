package com.grippaweb.server.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class BasicSecurityConfiguration extends WebSecurityConfigurerAdapter
{
	@Value("${basic.security.userpwd}")  
    String user_password;

	@Value("${basic.security.adminpwd}")  
    String admin_password; 
	
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder()
	{
		return new BCryptPasswordEncoder();
	};
	

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception 
	{
	    auth
	        .inMemoryAuthentication()
	        .withUser("user")
	        .password(new BCryptPasswordEncoder().encode(user_password))
	        .roles("USER")
	    .and()
	        .withUser("admin")
	        .password(new BCryptPasswordEncoder().encode(admin_password))
	        .roles("USER", "ACTUATOR");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception 
	{
	    http
	        .csrf()
	        .disable()
	        .httpBasic()
	     .and()
	        .authorizeRequests()
	        .antMatchers("/actuator/**").hasAuthority("ROLE_ACTUATOR")
	        .antMatchers("/**").hasAuthority("ROLE_USER");
	}
}

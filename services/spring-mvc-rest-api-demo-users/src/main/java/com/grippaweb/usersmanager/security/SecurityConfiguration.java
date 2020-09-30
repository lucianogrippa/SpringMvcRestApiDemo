package com.grippaweb.usersmanager.security;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import com.grippaweb.usersmanager.entities.Roles;
import com.grippaweb.usersmanager.services.RolesService;
import com.grippaweb.usersmanager.services.UserService;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    private static String REALM = "REAME";

    private static final String[] USER_MATCHER = { "/api/users/find/**" };
    private static final String[] ADMIN_MATCHER = { "/api/users/manage/**" };

    @Autowired
    UserService userService;

    @Autowired
    RolesService roleService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
	http.csrf().disable().authorizeRequests().antMatchers(USER_MATCHER).hasAnyRole("USER")
		.antMatchers(ADMIN_MATCHER).hasAnyRole("ADMIN").anyRequest().authenticated().and().httpBasic()
		.realmName(REALM)
		.authenticationEntryPoint(getBasicAuthEntryPoint())
		.and().sessionManagement()
		.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Bean
    public AuthEntryPoint getBasicAuthEntryPoint() {
	return new AuthEntryPoint();
    }

    @Override
    public void configure(WebSecurity web) {
	web.ignoring().antMatchers(HttpMethod.OPTIONS, "/**");
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
	return new BCryptPasswordEncoder();
    };

    @Bean
    @Override
    public UserDetailsService userDetailsService() {
	UserBuilder users = User.builder();

	// seleziona gli utenti che sono amministratori
	InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();

	Roles roleAdmin = roleService.find("ROLE_ADMIN");

	if (roleAdmin != null && roleAdmin.getId() > 0) {

	    List<com.grippaweb.usersmanager.entities.User> dbUsers = userService.listByRole(roleAdmin);

	    if (dbUsers != null) {
		for(com.grippaweb.usersmanager.entities.User userItem : dbUsers) {
		    manager.createUser(users
			    		.username(userItem.getUsername())
			    		.password(new BCryptPasswordEncoder()
			    			.encode(userItem.getSecret()))
				.roles("USER","ADMIN").build());
		}
	    }
	}

	///////////////// CREATE DEFAULT USERS //////////////////////
	createDefaultUsers(users, manager);

	return manager;
    }

    private void createDefaultUsers(UserBuilder users, InMemoryUserDetailsManager manager) {
	manager.createUser(users.username("ReadUser").password(new BCryptPasswordEncoder().encode("BimBumBam_2018"))
		.roles("USER").build());

	manager.createUser(users.username("Admin").password(new BCryptPasswordEncoder().encode("MagicaBula_2018"))
		.roles("USER", "ADMIN").build());
    }
}

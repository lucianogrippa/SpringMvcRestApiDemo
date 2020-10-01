package com.grippaweb.usersmanager.security;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    private static final String[] USER_MATCHER = { "/api/users/**",
	    				          "/api/roles/**"  
	    					 };
    private static final String[] ADMIN_MATCHER = { "/actuator/**" };
    

    @Autowired
    UserService userService;

    @Autowired
    RolesService roleService;
    
    @Value("${application.default-user.name}")
    private String defaultUserName;
    
    @Value("${application.default-user.password}")
    private String defaultUserPassword;
    
    @Value("${application.default-user-admin.name}")
    private String defaultAdminUserName;
    
    @Value("${application.default-user-admin.password}")
    private String defaultAdminUserPassword;

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
		// amministratori da database
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
	manager.createUser(users.username(defaultUserName).password(new BCryptPasswordEncoder().encode(defaultUserPassword))
		.roles("USER").build());

	manager.createUser(users.username(defaultAdminUserName).password(new BCryptPasswordEncoder().encode(defaultAdminUserPassword))
		.roles("USER", "ADMIN").build());
    }
}

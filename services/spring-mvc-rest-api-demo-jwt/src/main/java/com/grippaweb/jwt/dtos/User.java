package com.grippaweb.jwt.dtos;

import java.util.Collection;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

	private long userId;
	private String firstname;
	private String lastname;
	private String secret;
	private String email;
	private String username;
	private boolean active;
	private Date creationtimestamp;
	private Date lastupdate;
	private Date lastaccess;
	private Collection<Roles> roles;
	
	public User(long id) {
		userId = id;
	}
}
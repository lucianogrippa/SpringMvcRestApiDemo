package com.grippaweb.usersmanager.entities;

import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashSet;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Type;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long userId;

	@Column(nullable = false)
	private String firstname;

	@Column(nullable = false)
	private String lastname;

	@Column(nullable = false)
	private String secret;

	@Column(nullable = false)
	private String email;

	@Column(nullable = false)
	private String username;

	@Column(nullable = false, columnDefinition = "BIT", length = 1)
	@Type(type = "org.hibernate.type.NumericBooleanType")
	private boolean active;

	@Basic(optional = false)
	@Column(name = "creationtimestamp", insertable = false, updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date creationtimestamp;

	@Temporal(TemporalType.TIMESTAMP)
	private Date lastupdate;

	@Temporal(TemporalType.TIMESTAMP)
	private Date lastaccess;

	// ruoli di appartenenza utenti
	@ManyToMany(targetEntity = Roles.class,fetch=FetchType.EAGER)
	@JoinTable(name = "usersroles", joinColumns = { @JoinColumn(name = "userid") }, inverseJoinColumns = {
			@JoinColumn(name = "roleid") })
	private Collection<Roles> roles = new  LinkedHashSet<Roles>();
	
	public User(long id) {
		userId = id;
	}
}
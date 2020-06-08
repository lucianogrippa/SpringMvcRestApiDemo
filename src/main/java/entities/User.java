package entities;

import java.sql.Date;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Type;

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
	
	@Column(nullable = false)
	@Type(type = "org.hibernate.type.NumericBooleanType")
	private boolean active;
	
	@Basic(optional = false)
	@Column(name = "creationtimestamp", insertable = false, updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	
	private Date creationtimestamp;
	@Temporal(TemporalType.DATE)
	private Date lastupdate;
	
	@Temporal(TemporalType.DATE)
	private Date lastaccess;
	
	// ruoli di appartenenza utenti
	@ManyToMany(targetEntity = Roles.class)
	private Set<Roles> roles;
	

	public User(long id) {
		userId = id;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public long getId() {
		return userId;
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public Date getCreationtimestamp() {
		return creationtimestamp;
	}

	public void setCreationtimestamp(Date creationtimestamp) {
		this.creationtimestamp = creationtimestamp;
	}

	public Date getLastupdate() {
		return lastupdate;
	}

	public void setLastupdate(Date lastupdate) {
		this.lastupdate = lastupdate;
	}

	public Date getLastaccess() {
		return lastaccess;
	}

	public void setLastaccess(Date lastaccess) {
		this.lastaccess = lastaccess;
	}

	public Set<Roles> getRoles() {
		return roles;
	}

	public void setRoles(Set<Roles> roles) {
		this.roles = roles;
	}
}
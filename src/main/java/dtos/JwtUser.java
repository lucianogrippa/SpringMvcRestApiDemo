package dtos;

import org.springframework.util.StringUtils;

import entities.User;

public class JwtUser {
	private long id;
	private String firstName;
	private String lastName;
	private String email;
    private String username;
    private String role;
    private String jwt;
    
    public static JwtUser fromUser(User user) {
    	if(user != null && !StringUtils.isEmpty(user.getUsername())) {
    		JwtUser jUser = new JwtUser();
    	    jUser.setEmail(user.getEmail());
    	    jUser.setFirstName(user.getFirstname());
    	    jUser.setId(user.getId());
    	    jUser.setLastName(user.getLastname());
    	    jUser.setRole(user.getRole());
    	    jUser.setUsername(user.getUsername());
    	    
    	    return jUser;
    	}
    	return null;
    }
    
	public String getUsername() {
		return username;
	}
	public void setUsername(String userName) {
		this.username = userName;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getJwt() {
		return jwt;
	}

	public void setJwt(String jwt) {
		this.jwt = jwt;
	}
}

package entities;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "usersroles")
public class UsersRoles {

	private long userid;
	private long roleid;

	// role foreignkey ->roleid
	@ManyToOne(targetEntity = Roles.class)
	private Roles roles;

	// users -> foregn key userid
	@ManyToOne(targetEntity = User.class)
	private User users;

	public long getUserid() {
		return userid;
	}

	public void setUserid(long userid) {
		this.userid = userid;
	}

	public long getRoleid() {
		return roleid;
	}

	public void setRoleid(long roleid) {
		this.roleid = roleid;
	}

	public User getUsers() {
		return users;
	}

	public void setUsers(User users) {
		this.users = users;
	}

	public Roles getRoles() {
		return roles;
	}

	public void setRoles(Roles roles) {
		this.roles = roles;
	}
}

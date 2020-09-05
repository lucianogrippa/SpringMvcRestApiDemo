package entities;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2020-09-01T12:37:42.469+0200")
@StaticMetamodel(User.class)
public class User_ {
	public static volatile SingularAttribute<User, Long> userId;
	public static volatile SingularAttribute<User, String> firstname;
	public static volatile SingularAttribute<User, String> lastname;
	public static volatile SingularAttribute<User, String> secret;
	public static volatile SingularAttribute<User, String> email;
	public static volatile SingularAttribute<User, String> username;
	public static volatile SingularAttribute<User, Boolean> active;
	public static volatile SingularAttribute<User, Date> creationtimestamp;
	public static volatile SingularAttribute<User, Date> lastupdate;
	public static volatile SingularAttribute<User, Date> lastaccess;
	public static volatile CollectionAttribute<User, Roles> roles;
}

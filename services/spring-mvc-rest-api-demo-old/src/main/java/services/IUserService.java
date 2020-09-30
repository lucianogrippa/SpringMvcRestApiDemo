package services;

import java.util.List;

import entities.User;

public interface IUserService {
	User authUser(String username, String pwd, String appKey);

	User find(long id);
	
	List<User> findUsersByRole(String role);

	User authUserWithUUID(String username, long userId);

	User authUserByRequestAuthToken(String authToken);

	boolean save(User user);

	boolean delete(long userId);
	
	List<User> listAll();
}

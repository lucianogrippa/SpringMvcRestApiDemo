package dao;

import java.util.List;

import entities.User;

public interface IUserRepository {
	User findByCredential(String username,String pwd);
	User findById(long id);
	User findByUsername(String username);
	User findByIdUsername(String username, long userId);
	User findByRequestAuthToken(String authToken);
	long save(User user);
	boolean remove(long id);
	List<User> list();
}

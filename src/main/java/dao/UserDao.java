package dao;

import entities.User;

public interface UserDao {
	User findByCredential(String username,String pwd);
	User findByIdUsername(String username, long userId);
	User findByRequestAuthToken(String authToken);
}

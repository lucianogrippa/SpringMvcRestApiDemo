package services;

import entities.User;

public interface IUserService {
	User authUser(String username,String pwd,String appKey);
	public User find(long id);
	 User authUserWithToken(String username, long userId);
}

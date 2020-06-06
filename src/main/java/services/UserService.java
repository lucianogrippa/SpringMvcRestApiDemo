package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import entities.User;
import repositories.UserRepository;

@Service(value = "userService")
public class UserService implements IUserService {

	@Value("${app.key}")
	String apiKey;
	
	
	@Autowired
	UserRepository repository;

	@Override
	public User authUser(String username, String pwd, String appKey) {

		if(!StringUtils.isEmpty(appKey) && appKey.equals(apiKey)) {
			User dbUser = repository.findByCredential(username, pwd);
			if(dbUser != null) {
				return dbUser;
			}
		}
		return null;
	}

	@Override
	public User find(long id) {
		return null;
	}
	@Override
	public User authUserWithUUID(String username, long userId) {
		return repository.findByIdUsername(username,userId);
	}

	@Override
	public User authUserByRequestAuthToken(String authToken) {
		
		return repository.findByRequestAuthToken(authToken);
	}
	
	
}

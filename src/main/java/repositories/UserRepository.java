package repositories;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import dao.UserDao;
import entities.User;
import helpers.AppPropertiesHelper;
import helpers.LogHelper;

@Component(value = "userRepository")
public class UserRepository implements UserDao {

	@Autowired
	Environment env;

	@Autowired
	AppPropertiesHelper appPropertiesHelper;

	@Autowired
	LogHelper logger;


	/**
	 * Nell 'esempio viene ricercato attraverso le porperties
	 */
	@Override
	public User findByCredential(String username, String pwd) {

		String dbUsername = appPropertiesHelper.getAppUsername();
		String dbPwd = appPropertiesHelper.getAppUserPwd();

		if (!StringUtils.isEmpty(username) && !StringUtils.isEmpty(dbPwd) && dbUsername.contentEquals(username)
				&& dbPwd.contentEquals(pwd)) {
			User usr = new User(appPropertiesHelper.getAppUserId());
			usr.setFirstname(appPropertiesHelper.getAppUserFirstname());
			usr.setLastname(appPropertiesHelper.getAppUserLastName());
			usr.setUsername(appPropertiesHelper.getAppUsername());
			usr.setRole(appPropertiesHelper.getAppUserRole());
			usr.setEmail(appPropertiesHelper.getAppUserPwd());

			return usr;
		}
		return null;
	}

	@Override
	public User findByIdUsername(String username, long userId) {
		if(!StringUtils.isEmpty(username) && userId>0) {
			String dbUsername = appPropertiesHelper.getAppUsername();
			long dbId = appPropertiesHelper.getAppUserId();

			if (!StringUtils.isEmpty(username) && dbId>0 && dbUsername.contentEquals(username)
					&& userId == dbId) {
				User usr = new User(appPropertiesHelper.getAppUserId());
				usr.setFirstname(appPropertiesHelper.getAppUserFirstname());
				usr.setLastname(appPropertiesHelper.getAppUserLastName());
				usr.setUsername(appPropertiesHelper.getAppUsername());
				usr.setRole(appPropertiesHelper.getAppUserRole());
				usr.setEmail(appPropertiesHelper.getAppUserPwd());

				return usr;
			}
		}
		return null;
	}

	public User findByRequestAuthToken(String authToken) {
		if(!StringUtils.isEmpty(authToken)) {
			String dbUsername = appPropertiesHelper.getAppUsername();
			String dbPwd = appPropertiesHelper.getAppUserPwd();
			String dbAppKey = appPropertiesHelper.getAppKey();
			String cleanToken = String.format("%s@%s@%s", dbUsername,dbPwd,dbAppKey);
			
			String dbRequestToken = DigestUtils.sha256Hex(cleanToken);  
			if (!StringUtils.isEmpty(dbRequestToken) && dbRequestToken.equals(authToken)) {
				User usr = new User(appPropertiesHelper.getAppUserId());
				usr.setFirstname(appPropertiesHelper.getAppUserFirstname());
				usr.setLastname(appPropertiesHelper.getAppUserLastName());
				usr.setUsername(appPropertiesHelper.getAppUsername());
				usr.setRole(appPropertiesHelper.getAppUserRole());
				usr.setEmail(appPropertiesHelper.getAppUserPwd());

				return usr;
			}
		}
		return null;
	}
}

package repositories;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import dao.UserDao;
import entities.User;
import helpers.AppPropertiesHelper;
import helpers.LogHelper;

@Repository(value = "userRepository")
public class UserRepository implements PagingAndSortingRepository<User, Long>, UserDao {

	@Autowired
	Environment env;

	@Autowired
	AppPropertiesHelper appPropertiesHelper;

	@Autowired
	LogHelper logger;

	@Override
	public <S extends User> S save(S entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends User> Iterable<S> save(Iterable<S> entities) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User findOne(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean exists(Long id) {

		return false;
	}

	@Override
	public Iterable<User> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterable<User> findAll(Iterable<Long> ids) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long count() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(User entity) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(Iterable<? extends User> entities) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub

	}

	@Override
	public Iterable<User> findAll(Sort sort) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<User> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

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

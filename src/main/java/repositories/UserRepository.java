package repositories;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.TimeZone;

import javax.persistence.NoResultException;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import dao.AbstractDao;
import dao.IUserRepository;
import entities.User;
import helpers.AppPropertiesHelper;
import helpers.LogHelper;

@Service(value = "userRepository")
public class UserRepository extends AbstractDao<Long, User> implements IUserRepository {

	@Autowired
	AppPropertiesHelper properties;

	@Autowired
	LogHelper logger;

	public UserRepository() {
	}

	/**
	 * Nell 'esempio viene ricercato attraverso le porperties
	 */
	@Override
	@Transactional(readOnly = true)
	public User findByCredential(String username, String pwd) {

		try {
			User user = getEntityManager()
					.createQuery("from User u where u.username = :uid and u.secret = :sec ", User.class)
					.setParameter("uid", username).setParameter("sec", pwd).getSingleResult();

			return user;
		} catch (NoResultException ex) {
			logger.logDebug(ex.getMessage());
			return null;
		}
	}

	@Override
	@Transactional(readOnly = true)
	public User findByIdUsername(String username, long userId) {
		try {
			User user = getEntityManager()
					.createQuery("from User u where u.id = :uid and u.username = :name", User.class)
					.setParameter("uid", userId).setParameter("name", username).getSingleResult();

			return user;
		} catch (NoResultException ex) {
			return null;
		}
	}

	@Transactional(readOnly = true)
	public User findByRequestAuthToken(String authToken) {
		if (!StringUtils.isEmpty(authToken)) {

			List<User> userList = list();
			if (userList != null) {
				try {
					Optional<User> user = userList.stream().map(u -> {
						String cleanToken = String.format("%s@%s@%s", u.getUsername(), u.getSecret(),
								properties.getAppKey());
						String dbRequestToken = DigestUtils.sha256Hex(cleanToken);
						u.setSecret(dbRequestToken);
						return u;
					}).filter(e -> {
						String scp = e.getSecret();
						return scp.equals(authToken);
					}).findFirst();

					return user.get();
				} catch (Exception e) {
					logger.logException(e);
				}
			}
		}
		return null;

	}

	@Override
	@Transactional(readOnly = true)
	public List<User> list() {
		List<User> users = getEntityManager().createQuery("from User u order by u.firstname", User.class)
				.getResultList();
		return users;
	}

	@Override
	@Transactional(readOnly = true)
	public User findById(long id) {
		return getByKey(id);
	}

	@Override
	@Transactional(readOnly = true)
	public User findByUsername(String username) {
		try {
			User user = getEntityManager().createQuery("from User u where u.username = :name", User.class)
					.setParameter("name", username).getSingleResult();

			return user;
		} catch (NoResultException ex) {
			return null;
		}
	}

	@Override
	@Transactional
	public long save(User user) {
		// check i exists in db
		long userId = -1;
		User userToSave = new User();
		userToSave = user.getId() > 0 ? findById(user.getId()) : findByUsername(user.getUsername());
		if(userToSave == null )
			userToSave = new User();
		
		userToSave.setActive(user.isActive());
		userToSave.setFirstname(user.getFirstname());
		userToSave.setLastname(user.getLastname());
		userToSave.setEmail(user.getEmail());
		userToSave.setLastaccess(user.getLastaccess());
		userToSave.setRoles(user.getRoles());
		userToSave.setSecret(user.getSecret());
		userToSave.setUsername(user.getUsername());

		
		if (userToSave.getId() > 0) {
			userToSave.setUserId(userToSave.getId());
			
			try {
				userToSave.setCreationtimestamp(userToSave.getCreationtimestamp());
				userToSave.setLastupdate(Calendar.getInstance().getTime());
				update(userToSave);
				userId = userToSave.getId();
			} catch (Exception e) {
				logger.logException(e);
			}
		} else {
			try {
				persist(userToSave);
				userId = userToSave.getId();
			} catch (Exception e) {
				logger.logException(e);
			}
		}

		return userId;
	}

	@Override
	@Transactional
	public boolean remove(long id) {
		boolean userDeleted = false;
		try {
			User user = getByKey(id);
			if (user != null && !StringUtils.isEmpty(user.getUsername())) {
				delete(user);
				userDeleted = true;
			}
		} catch (Exception e) {
			logger.logException(e);
		}
		return userDeleted;
	}

}

package repositories;

import java.util.List;
import java.util.Optional;

import javax.persistence.NoResultException;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import dao.AbstractDao;
import dao.UserDao;
import entities.Roles;
import entities.User;
import helpers.AppPropertiesHelper;
import helpers.LogHelper;

@Service
public class UserRepository extends AbstractDao<Long, User> implements UserDao {

	@Autowired
	AppPropertiesHelper properties;

	@Autowired
	LogHelper logger;

	/**
	 * Nell 'esempio viene ricercato attraverso le porperties
	 */
	@Override
	public User findByCredential(String username, String pwd) {

		try {
			User user = getEntityManager()
					.createQuery("from User u where u.username = :uid and u.secret = :sec ",User.class)
					.setParameter("uid", username).setParameter("sec", pwd).getSingleResult();

			return user;
		} catch (NoResultException ex) {
			logger.logDebug(ex.getMessage());
			return null;
		}
	}

	@Override
	public User findByIdUsername(String username, long userId) {
		try {
			User user = getEntityManager()
					.createQuery("from User u where u.id = :uid and u.username = :name",User.class)
					.setParameter("uid", userId).setParameter("name", username).getSingleResult();

			return user;
		} catch (NoResultException ex) {
			return null;
		}
	}

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
					}).filter(e -> e.getSecret().equals(authToken)).findFirst();

					return user.get();
				} catch (Exception e) {
					logger.logException(e);
				}
			}
		}
		return null;
	}

	@Override
	public boolean add(User user) {
		return false;
	}

	@Override
	public boolean addInrole(User user, Roles roles) {
		return false;
	}

	@Override
	public boolean delete(long id) {
		return false;
	}

	@Override
	public List<User> list() {
		List<User> users = getEntityManager().createQuery("from User u order by u.firstname",User.class)
				.getResultList();
		return users;
	}

	@Override
	public User findById(long id) {
		return getByKey(id);
	}
}

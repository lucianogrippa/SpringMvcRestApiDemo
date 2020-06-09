package repositories;

import java.util.List;
import java.util.Optional;

import javax.persistence.NoResultException;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import dao.AbstractDao;
import dao.UserDao;
import entities.Roles;
import entities.User;
import helpers.AppPropertiesHelper;
import helpers.LogHelper;

@Repository("userDao")
public class UserRepository extends AbstractDao<Integer, User> implements UserDao {

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
			User user = (User) getEntityManager()
					.createQuery("SELECT u FROM User u WHERE u.username = :uid AND u.secret = :sec ")
					.setParameter("uid", username).setParameter("sec", pwd).getSingleResult();

			return user;
		} catch (NoResultException ex) {
			return null;
		}
	}

	@Override
	public User findByIdUsername(String username, long userId) {
		try {
			User user = (User) getEntityManager()
					.createQuery("SELECT u FROM User u WHERE u.id = :uid AND u.username = :name ")
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
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addInrole(User user, Roles roles) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(long id) {
		// TODO Auto-generated method stub
		return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> list() {
		List<User> users = getEntityManager().createQuery("SELECT u FROM User u ORDER BY u.firstname ASC")
				.getResultList();
		return users;
	}
}

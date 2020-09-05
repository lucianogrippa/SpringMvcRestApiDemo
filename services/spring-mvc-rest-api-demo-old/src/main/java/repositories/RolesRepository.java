/**
 * 
 */
package repositories;

import java.util.List;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import dao.AbstractDao;
import dao.IRolesRepository;
import entities.Roles;
import helpers.LogHelper;

@Service(value = "rolesRepository")
public class RolesRepository extends AbstractDao<Long, Roles> implements IRolesRepository {

	@Autowired
	LogHelper logger;

	@Override
	@Transactional(readOnly = true)
	public Roles findById(long id) {
		return getByKey(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Roles findByCode(String code) {
		try {
			Roles roles = getEntityManager().createQuery("from Roles r where r.code = :code", Roles.class)
					.setParameter("code", code).getSingleResult();

			return roles;
		} catch (NoResultException ex) {
			logger.logDebug(ex.getMessage());
			return null;
		}
	}

	@Override
	@Transactional(readOnly = true)
	public List<Roles> listAll() {
		try {
			List<Roles> roles = getEntityManager().createQuery("from Roles r order by r.name", Roles.class)
					.getResultList();

			return roles;
		} catch (NoResultException ex) {
			logger.logDebug(ex.getMessage());
			return null;
		}
	}

	@Override
	@Transactional
	public long save(Roles role) {
		// check i exists in db
		long roleId = -1;
		Roles roleToSave = new Roles();
		roleToSave = role.getId() > 0 ? findById(role.getId()) : findByCode(role.getCode());
		if (roleToSave == null)
			roleToSave = new Roles();

		roleToSave.setName(role.getName());
		roleToSave.setCode(role.getCode());

		if (roleToSave.getId() > 0) {
			try {
				update(roleToSave);
				roleId = roleToSave.getId();
			} catch (Exception e) {
				logger.logException(e);
			}
		} else {
			try {
				persist(roleToSave);
				roleId = roleToSave.getId();
			} catch (Exception e) {
				logger.logException(e);
			}
		}

		return roleId;
	}

	@Override
	@Transactional
	public boolean remove(long id) {
		boolean roleDeleted = false;
		try {
			Roles role = getByKey(id);
			if (role != null && !StringUtils.isEmpty(role.getCode())) {
				delete(role);
				roleDeleted = true;
			}
		} catch (Exception e) {
			logger.logException(e);
		}
		return roleDeleted;
	}

}

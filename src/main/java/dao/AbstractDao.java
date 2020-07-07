package dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Scope(proxyMode = ScopedProxyMode.INTERFACES)
@Transactional
public abstract class AbstractDao<PK extends Serializable, T> {

	private final Class<T> persistentClass;

	@SuppressWarnings("unchecked")
	public AbstractDao() {
		this.persistentClass =  (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass())
				.getActualTypeArguments()[1];
	}

	@PersistenceContext(unitName = "restApiJPAUnit")
	private EntityManager entityManager;

	protected EntityManager getEntityManager() {
		return this.entityManager;
	}

	protected T getByKey(PK key) {
		return entityManager.find(persistentClass, key);
	}

	protected void persist(T entity) throws EntityExistsException {
		entityManager.persist(entity);
	}

	protected void update(T entity) {
		entityManager.merge(entity);
	}

	protected void delete(T entity) {
		entityManager.remove(entity);
	}

}

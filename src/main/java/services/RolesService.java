/**
 * 
 */
package services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import entities.Roles;
import helpers.LogHelper;
import repositories.RolesRepository;

@Service(value = "rolesService")
public class RolesService implements IRolesService {

	@Autowired
	RolesRepository repository;
	
	@Autowired
	LogHelper logger;
	
	@Override
	public Roles find(long id) {
		return repository.findById(id);
	}

	@Override
	public Roles find(String code) {
		return repository.findByCode(code);
	}

	@Override
	public List<Roles> listAll() {
		return repository.listAll();
	}

	@Override
	public boolean save(Roles role) {
		if(role != null) {
			long roleId = repository.save(role);
			
			return roleId >0;
		}
		return false;
	}

	@Override
	public boolean remove(long id) {
		return repository.remove(id);
	}

}

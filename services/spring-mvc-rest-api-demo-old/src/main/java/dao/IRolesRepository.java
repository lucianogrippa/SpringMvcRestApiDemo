package dao;

import java.util.List;

import entities.Roles;

public interface IRolesRepository {
   Roles findById(long id);
   Roles findByCode(String code);
   List<Roles> listAll();
   long save(Roles role);
   boolean remove(long id);
}

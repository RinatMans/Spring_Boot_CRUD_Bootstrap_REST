package web_crud.dao;

import org.springframework.stereotype.Repository;
import web_crud.model.Role;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class RoleDaoImp implements RoleDao{

    @PersistenceContext
    private EntityManager manager;

    @Override
    public void saveRole(Role role) {
        if (findRoleByRoleName(role.getRole()) != null) {
            manager.merge(role);
        }
        manager.persist(role);
    }

    @Override
    public List<Role> getAllRoles() {
        return manager.createQuery(" select role from Role role", Role.class).getResultList();
    }

    @Override
    public Role findRoleByRoleName(String role) {
        TypedQuery<Role> query = manager.createQuery("select role from Role role  where  role.role=:paramRole", Role.class);
        query.setParameter("paramRole", role);
        return query.getResultList().stream().findAny().orElse(null);
    }
}

package web_crud.dao;

import web_crud.model.Role;

import java.util.List;

public interface RoleDao {
    void saveRole(Role role);

    List<Role> getAllRoles();

    Role findRoleByRoleName(String RoleName);
}

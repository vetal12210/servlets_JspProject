package dao;

import entity.Role;

public interface RoleDao extends Dao<Role> {
    Role findByName(String name);
}

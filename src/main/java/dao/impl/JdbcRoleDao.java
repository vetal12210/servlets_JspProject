package dao.impl;

import constants.Constants;
import dao.GenericJdbcDao;
import dao.RoleDao;
import entity.Role;
import lombok.extern.log4j.Log4j2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Log4j2
public class JdbcRoleDao extends GenericJdbcDao<Role> implements RoleDao {

    private Connection connection = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;

    public JdbcRoleDao() {
        super();
    }

    @Override
    public void create(Role role) {
        log.info("Create role {}", role);
        try {
            getConnection();
            preparedStatement = connection.prepareStatement(Constants.CREATE_ROLE);
            preparedStatement.setString(1, role.getName());
            executeAndCommit();
        } catch (SQLException e) {
            rollbackAndThrowEx(connection, e);
        } finally {
            closeAndCatchEx(preparedStatement, connection);
        }
    }

    @Override
    public void update(Role role) {
        log.info("Update role {}", role);
        try {
            getConnection();
            preparedStatement = connection.prepareStatement(Constants.UPDATE_ROLE);
            preparedStatement.setString(1, role.getName());
            preparedStatement.setLong(2, role.getId());
            executeAndCommit();
        } catch (SQLException e) {
            rollbackAndThrowEx(connection, e);
        } finally {
            closeAndCatchEx(preparedStatement, connection);
        }
    }

    @Override
    public void remove(Role role) {
        log.info("Delete role {}", role);
        try {
            getConnection();
            preparedStatement = connection.prepareStatement(Constants.REMOVE_ROLE);
            preparedStatement.setLong(1, role.getId());
            executeAndCommit();
        } catch (SQLException e) {
            rollbackAndThrowEx(connection, e);
        } finally {
            closeAndCatchEx(preparedStatement, connection);
        }
    }

    @Override
    public List<Role> findAll() {
        log.info("Find all roles");
        List<Role> roleList = new ArrayList<>();
        try {
            getConnection();
            preparedStatement = connection.prepareStatement(Constants.FIND_ALL_ROLES);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String roleName = resultSet.getString("name");
                long id = resultSet.getLong("id");
                roleList.add(new Role(id, roleName));
            }
            commitIt(connection);
        } catch (SQLException e) {
            rollbackAndThrowEx(connection, e);
        } finally {
            closeAndCatchEx(resultSet, preparedStatement, connection);
        }
        return roleList;
    }

    @Override
    public Role findById(Long id) {
        log.info("Find role by id {}", id);
        Role role = new Role();
        try {
            getConnection();
            preparedStatement = connection.prepareStatement(Constants.FIND_ROLE_BY_ID);
            preparedStatement.setLong(1, id);
            getRole(role);
        } catch (SQLException e) {
            rollbackAndThrowEx(connection, e);
        } finally {
            closeAndCatchEx(resultSet, preparedStatement, connection);
        }
        return role;
    }

    @Override
    public Role findByName(String name) {
        log.info("Find role by name {}", name);
        Role role = new Role();
        try {
            getConnection();
            preparedStatement = connection.prepareStatement(Constants.FIND_ROLE_BY_NAME);
            preparedStatement.setString(1, name);
            getRole(role);
        } catch (SQLException e) {
            rollbackAndThrowEx(connection, e);
        } finally {
            closeAndCatchEx(resultSet, preparedStatement, connection);
        }
        return role;
    }

    private void getRole(Role role) throws SQLException {
        resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            String roleName = resultSet.getString("name");
            Long roleId = resultSet.getLong("id");
            role.setName(roleName);
            role.setId(roleId);
        }
        commitIt(connection);
    }

    private void executeAndCommit() throws SQLException {
        preparedStatement.execute();
        commitIt(connection);
    }

    public void getConnection() throws SQLException {
        connection = GenericJdbcDao.getBasicDataSource().getConnection();
        connection.setAutoCommit(false);
    }
}
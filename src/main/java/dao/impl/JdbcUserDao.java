package dao.impl;

import constants.Constants;
import dao.GenericJdbcDao;
import dao.UserDao;
import entity.User;
import lombok.extern.log4j.Log4j2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Log4j2
public class JdbcUserDao extends GenericJdbcDao<User> implements UserDao {

    private Connection connection = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;


    public JdbcUserDao() {
        super();
    }

    @Override
    public void create(User user) {
        log.info("Create user {}", user);
        try {
            getConnection();
            preparedStatement = connection.prepareStatement(Constants.CREATE_USER);
            entitySet(preparedStatement, user);
            executeAndCommit();
        } catch (SQLException e) {
            rollbackAndThrowEx(connection, e);
        } finally {
            closeAndCatchEx(preparedStatement, connection);
        }
    }

    @Override
    public void update(User user) {
        log.info("Update user {}", user);
        try {
            getConnection();
            preparedStatement = connection.prepareStatement(Constants.UPDATE_USER);
            entitySet(preparedStatement, user);
            preparedStatement.setLong(8, user.getId());
            executeAndCommit();
        } catch (SQLException e) {
            rollbackAndThrowEx(connection, e);
        } finally {
            closeAndCatchEx(preparedStatement, connection);
        }
    }

    @Override
    public void remove(User user) {
        log.info("Remove user {}", user);
        try {
            getConnection();
            preparedStatement = connection.prepareStatement(Constants.REMOVE_USER);
            preparedStatement.setLong(1, user.getId());
            executeAndCommit();
        } catch (SQLException e) {
            rollbackAndThrowEx(connection, e);
        } finally {
            closeAndCatchEx(preparedStatement, connection);
        }
    }

    @Override
    public List<User> findAll() {
        log.info("Find all users");
        List<User> userList = new ArrayList<>();
        try {
            getConnection();
            preparedStatement = connection.prepareStatement(Constants.FIND_ALL_USERS);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                User user = new User();
                userSet(user, resultSet);
                userList.add(user);
            }
            commitIt(connection);
        } catch (SQLException e) {
            rollbackAndThrowEx(connection, e);
        } finally {
            closeAndCatchEx(preparedStatement, connection);
        }
        return userList;
    }

    @Override
    public User findById(Long id) {
        log.info("Find user by id {}", id);
        User user = new User();
        try {
            getConnection();
            preparedStatement = connection.prepareStatement(Constants.FIND_USER_BY_ID);
            preparedStatement.setLong(1, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                userSet(user, resultSet);
            }
            commitIt(connection);
        } catch (SQLException e) {
            rollbackAndThrowEx(connection, e);
        } finally {
            closeAndCatchEx(preparedStatement, connection);
        }
        return user;
    }

    @Override
    public User findByLogin(String login) {
        log.info("Find user by login {}", login);
        return getUser(login, Constants.FIND_USER_BY_LOGIN);
    }

    @Override
    public User findByEmail(String email) {
        log.info("Find user by email {}", email);
        return getUser(email, Constants.FIND_USER_BY_EMAIL);
    }

    private User getUser(String email, String findByEmail) {
        User user = new User();
        try {
            getConnection();
            preparedStatement = connection.prepareStatement(findByEmail);
            preparedStatement.setString(1, email);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                userSet(user, resultSet);
            }
            commitIt(connection);
        } catch (SQLException e) {
            rollbackAndThrowEx(connection, e);
        } finally {
            closeAndCatchEx(preparedStatement, connection);
        }
        return user;
    }

    private void userSet(User user, ResultSet resultSet) throws SQLException {
        user.setId(resultSet.getLong("id"));
        user.setLogin(resultSet.getString("login"));
        user.setPassword(resultSet.getString("password"));
        user.setEmail(resultSet.getString("email"));
        user.setFirstName(resultSet.getString("firstname"));
        user.setLastName(resultSet.getString("lastname"));
        user.setBirthday(resultSet.getDate("birthday"));
        user.setRole(resultSet.getLong("roles_id"));
    }

    private void entitySet(PreparedStatement preparedStatement, User entity) throws SQLException {
        preparedStatement.setString(1, entity.getLogin());
        preparedStatement.setString(2, entity.getPassword());
        preparedStatement.setString(3, entity.getEmail());
        preparedStatement.setString(4, entity.getFirstName());
        preparedStatement.setString(5, entity.getLastName());
        preparedStatement.setDate(6, entity.getBirthday());
        preparedStatement.setLong(7, entity.getRole());
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

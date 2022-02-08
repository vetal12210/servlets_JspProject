package dao;

import org.apache.commons.dbcp2.BasicDataSource;
import util.JdbcException;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public abstract class GenericJdbcDao<E> implements Dao<E> {
    private static BasicDataSource basicDataSource;

    public GenericJdbcDao() {
    }

    public static BasicDataSource getBasicDataSource() {
        if (basicDataSource == null) {
            basicDataSource = getDataSourceFromDb();
        }
        return basicDataSource;
    }

    private static BasicDataSource getDataSourceFromDb() {
        Properties props = loadProperties();
        BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setDriverClassName("org.h2.Driver");
        basicDataSource.setUrl(props.getProperty("db.url"));
        basicDataSource.setUsername(props.getProperty("db.user"));
        basicDataSource.setPassword(props.getProperty("db.password"));
        basicDataSource.setMinIdle(5);
        basicDataSource.setMaxIdle(10);
        basicDataSource.setMaxOpenPreparedStatements(100);
        return basicDataSource;
    }

    private static Properties loadProperties() {
        Properties props = new Properties();
        try (InputStream input = GenericJdbcDao.class.getResourceAsStream("/db.properties")) {
            props.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return props;
    }

    public static void commitIt(Connection connection) {
        try {
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void rollbackAndThrowEx(Connection connection, SQLException e) {
        if (connection != null) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        throw new JdbcException(e);
    }

    public void closeAndCatchEx(PreparedStatement preparedStatement, Connection connection) {
        try {
            if (preparedStatement != null) {
                preparedStatement.close();
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void closeAndCatchEx(ResultSet resultSet, PreparedStatement preparedStatement, Connection connection) {
        try {
            if (resultSet != null) {
                resultSet.close();
                preparedStatement.close();
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}

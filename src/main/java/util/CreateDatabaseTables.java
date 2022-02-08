package util;

import dao.GenericJdbcDao;
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateDatabaseTables {
    public static void createDbTables() throws SQLException {
        String createRolesTableSql =
                "CREATE TABLE IF NOT EXISTS ROLES ("
                        + "id IDENTITY PRIMARY KEY, "
                        + "name VARCHAR(64) NOT NULL"
                        + ")";
        String createUserTableSql =
                "CREATE TABLE IF NOT EXISTS USERS ("
                        + "id IDENTITY PRIMARY KEY, "
                        + "login VARCHAR(64) UNIQUE NOT NULL, "
                        + "password VARCHAR(64) NOT NULL, "
                        + "email VARCHAR(64) NOT NULL UNIQUE, "
                        + "firstname VARCHAR(64), "
                        + "lastname VARCHAR(64), "
                        + "birthday DATE NOT NULL, "
                        + "roles_id BIGINT NOT NULL, "
                        + "FOREIGN KEY (roles_id) REFERENCES roles (id)"
                        + ")";
        try {
            BasicDataSource dataSource = GenericJdbcDao.getBasicDataSource();
            Connection connection = dataSource.getConnection();
            Statement statement = connection.createStatement();
            statement.execute(createRolesTableSql);
            statement.execute(createUserTableSql);
            System.out.println("Tables are created!");
            statement.close();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

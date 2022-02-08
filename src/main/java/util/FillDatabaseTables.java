package util;

import dao.GenericJdbcDao;
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class FillDatabaseTables {
    public static void fillTables() throws SQLException {
        String fillUserTable1 = "INSERT INTO USERS (login, password, email, firstname, lastname, birthday, roles_id) VALUES ('admin', 'admin', 'admin@email', 'admin', 'admin', '1990-01-01', 1)";
        String fillUserTable2 = "INSERT INTO USERS (login, password, email, firstname, lastname, birthday, roles_id) VALUES ('user', 'user', 'user@email', 'user', 'user', '2000-02-02', 2)";
        String fillUserTable3 = "INSERT INTO USERS (login, password, email, firstname, lastname, birthday, roles_id) VALUES ('user2', 'user2', 'user2@email', 'user2', 'user2', '2003-02-02', 2)";
        String fillRolesTable1 = "INSERT INTO ROLES VALUES (DEFAULT, 'admin')";
        String fillRolesTable2 = "INSERT INTO ROLES VALUES (DEFAULT, 'user')";

        try {
            BasicDataSource dataSource = GenericJdbcDao.getBasicDataSource();
            Connection connection = dataSource.getConnection();
            Statement statement = connection.createStatement();
            statement.execute(fillRolesTable1);
            statement.execute(fillRolesTable2);
            statement.execute(fillUserTable1);
            statement.execute(fillUserTable2);
            statement.execute(fillUserTable3);
            System.out.println("Tables are filled!");
            statement.close();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

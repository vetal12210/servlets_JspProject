package util;

import dao.GenericJdbcDao;
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DropDatabaseTables {
    public static void dropTables() {
        String dropTablesSql = "DROP TABLE roles, users IF EXISTS";
        try {
            BasicDataSource dataSource = GenericJdbcDao.getBasicDataSource();
            Connection connection = dataSource.getConnection();
            Statement statement = connection.createStatement();
            statement.execute(dropTablesSql);
            System.out.println("Tables are dropped!");
            statement.close();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

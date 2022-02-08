package util;

import java.sql.SQLException;

public class JdbcException extends RuntimeException {
    public JdbcException(SQLException e) {
        throw new RuntimeException(e.getMessage(), e);
    }
}

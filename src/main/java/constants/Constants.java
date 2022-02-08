package constants;

public class Constants {
    public static final String FIND_ALL_USERS = "SELECT * FROM users";
    public static final String FIND_USER_BY_LOGIN = "SELECT * FROM users WHERE login = ?";
    public static final String FIND_USER_BY_ID = "SELECT * FROM users WHERE id = ?";
    public static final String FIND_USER_BY_EMAIL = "SELECT * FROM users WHERE email = ?";
    public static final String CREATE_USER = "INSERT INTO users (login, password, email, firstname, lastname, birthday, roles_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
    public static final String UPDATE_USER = "UPDATE users SET login = ?, password = ?, email = ?, firstname = ?, lastname = ?, birthday = ?, roles_id = ? WHERE id = ?";
    public static final String REMOVE_USER = "DELETE FROM users WHERE id=?";
    public static final String FIND_ALL_ROLES = "SELECT * FROM roles";
    public static final String FIND_ROLE_BY_ID = "SELECT * FROM roles WHERE id =?";
    public static final String FIND_ROLE_BY_NAME = "SELECT * FROM roles WHERE name=?";
    public static final String CREATE_ROLE = "INSERT INTO roles  (name) VALUES (?)";
    public static final String UPDATE_ROLE = "UPDATE roles SET name=? where id=?";
    public static final String REMOVE_ROLE = "DELETE FROM roles WHERE id=?";
}

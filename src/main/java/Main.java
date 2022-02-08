import dao.impl.JdbcRoleDao;
import dao.impl.JdbcUserDao;

public class Main {
    public static void main(String[] args) {
        JdbcUserDao jdbcUserDao = new JdbcUserDao();
        JdbcRoleDao jdbcRoleDao = new JdbcRoleDao();
        System.out.println("==========================");
        System.out.println(jdbcUserDao.findAll());
        System.out.println("==========================");
        System.out.println(jdbcRoleDao.findAll());
    }
}




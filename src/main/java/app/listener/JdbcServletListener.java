package app.listener;

import dao.impl.JdbcRoleDao;
import dao.impl.JdbcUserDao;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class JdbcServletListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext sc = sce.getServletContext();
        JdbcUserDao jdbcUserDao = new JdbcUserDao();
        JdbcRoleDao jdbcRoleDao = new JdbcRoleDao();

        sc.setAttribute("jdbcUserDao", jdbcUserDao);
        sc.setAttribute("jdbcRoleDao", jdbcRoleDao);
    }

    public void contextDestroyed(ServletContextEvent event) {
    }
}

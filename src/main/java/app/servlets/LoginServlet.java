package app.servlets;

import dao.impl.JdbcRoleDao;
import dao.impl.JdbcUserDao;
import entity.Role;
import entity.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {

    private JdbcUserDao jdbcUserDao;
    private JdbcRoleDao jdbcRoleDao;

    @Override
    public void init() {
        jdbcUserDao = (JdbcUserDao) getServletContext().getAttribute("jdbcUserDao");
        jdbcRoleDao = (JdbcRoleDao) getServletContext().getAttribute("jdbcRoleDao");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/login.jsp");
        requestDispatcher.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String login = request.getParameter("login");
            String password = request.getParameter("password");

            if (isUserExist(login, password)) {
                HttpSession session = request.getSession();
                User user = jdbcUserDao.findByLogin(login);
                Role role = jdbcRoleDao.findById(user.getRole());
                session.setAttribute("password", password);
                session.setAttribute("login", login);
                session.setAttribute("role", role);
                moveToMenu(response, role);
            } else {
                response.sendRedirect("/login");
            }
        } catch (Exception e) {
            getServletContext().getRequestDispatcher("/login.jsp").forward(request, response);
        }
    }

    private boolean isUserExist(String login, String password) {
        User user = jdbcUserDao.findByLogin(login);
        if (login == null || password == null) {
            return false;
        }
        return user.getPassword().equals(password);
    }

    private void moveToMenu(HttpServletResponse response, Role role) throws IOException {
        if (role.getName().equals("admin")) {
            response.sendRedirect("/admin");
        } else {
            response.sendRedirect("/user");
        }
    }
}
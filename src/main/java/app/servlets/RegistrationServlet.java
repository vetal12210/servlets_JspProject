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
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;

@WebServlet(urlPatterns = {"/admin/register"})
public class RegistrationServlet extends HttpServlet {
    private JdbcUserDao jdbcUserDao;
    private JdbcRoleDao jdbcRoleDao;

    @Override
    public void init() {
        jdbcUserDao = (JdbcUserDao) getServletContext().getAttribute("jdbcUserDao");
        jdbcRoleDao = (JdbcRoleDao) getServletContext().getAttribute("jdbcRoleDao");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("matchPasswords", "");
        request.setAttribute("emailExist", "");
        request.setAttribute("birthdayFuture", "");
        request.setAttribute("loginExist", "");
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/registration.jsp");
        requestDispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        User addUser = new User();
        addUser.setLogin(request.getParameter("login"));
        addUser.setPassword(request.getParameter("password"));
        addUser.setEmail(request.getParameter("email"));
        addUser.setFirstName(request.getParameter("lastName"));
        addUser.setLastName(request.getParameter("firstName"));
        addUser.setBirthday(Date.valueOf(request.getParameter("birthday")));
        Role createRole = jdbcRoleDao.findById(Long.valueOf(request.getParameter("role")));
        addUser.setRole(createRole.getId());

        if (isValidated(request)) {
            jdbcUserDao.create(addUser);
            response.sendRedirect("/admin");
        } else {
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/registration.jsp");
            requestDispatcher.forward(request, response);
        }
    }

    private boolean isValidated(HttpServletRequest request) {
        boolean isValid = true;
        if (jdbcUserDao.findByLogin(request.getParameter("login")).getLogin() != null) {
            request.setAttribute("loginExist", "This login already exist!");
            isValid = false;
        }
        if (!request.getParameter("password").equals(request.getParameter("password-repeat"))) {
            request.setAttribute("matchPasswords", "Passwords do not match!");
            isValid = false;
        }
        if (Date.valueOf(request.getParameter("birthday")).after(Date.valueOf(LocalDate.now()))) {
            request.setAttribute("birthdayFuture", "You in Future?!");
            isValid = false;
        }
        return isValid;
    }
}

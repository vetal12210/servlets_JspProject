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

@WebServlet(urlPatterns = {"/admin/edit-user"})
public class EditUserServlet extends HttpServlet {
    private JdbcUserDao jdbcUserDao;
    private JdbcRoleDao jdbcRoleDao;

    @Override
    public void init() {
        jdbcUserDao = (JdbcUserDao) getServletContext().getAttribute("jdbcUserDao");
        jdbcRoleDao = (JdbcRoleDao) getServletContext().getAttribute("jdbcRoleDao");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User editUser = jdbcUserDao.findByLogin(request.getParameter("editLogin"));
        request.setAttribute("editUser", editUser);
        request.setAttribute("matchPasswords", "");
        request.setAttribute("emailExist", "");
        request.setAttribute("birthdayFuture", "");
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/edit-user-page.jsp");
        requestDispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        User editUser = jdbcUserDao.findByLogin(request.getParameter("login"));
        if (!request.getParameter("password").isEmpty() && !request.getParameter("password-repeat").isEmpty()) {
            editUser.setPassword(request.getParameter("password"));
        }
        if (editUser == null) {
            getServletContext().getRequestDispatcher("/edit-user-page.jsp").forward(request, response);
        }
        if (validation(request, editUser)) {
            assert editUser != null;
            editUser.setEmail(request.getParameter("email"));
            editUser.setFirstName(request.getParameter("lastName"));
            editUser.setLastName(request.getParameter("firstName"));
            editUser.setBirthday(Date.valueOf(request.getParameter("birthday")));
            Role editRole = jdbcRoleDao.findById(Long.valueOf((request.getParameter("role"))));
            editUser.setRole(editRole.getId());
            jdbcUserDao.update(editUser);
            response.sendRedirect("/admin");
        } else {
            request.setAttribute("editUser", editUser);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/edit-user-page.jsp");
            requestDispatcher.forward(request, response);
        }
    }

    private boolean validation(HttpServletRequest request, User editUser) {
        boolean isValid = true;
        if (!request.getParameter("password").equals(request.getParameter("password-repeat"))) {
            editUser.setPassword("");
            request.setAttribute("matchPasswords", "Passwords do not match!");
            isValid = false;
        }
        if (!jdbcUserDao.findByLogin(request.getParameter("login")).getEmail().equals(request.getParameter("email"))) {
            editUser.setEmail("");
            request.setAttribute("emailExist", "This email already exist!");
            isValid = false;
        }
        if (Date.valueOf(request.getParameter("birthday")).after(Date.valueOf(LocalDate.now()))) {
            editUser.setBirthday(Date.valueOf(LocalDate.now()));
            request.setAttribute("birthdayFuture", "You in Future?!");
            isValid = false;
        }
        return isValid;
    }
}

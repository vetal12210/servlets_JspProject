package app.servlets;

import dao.impl.JdbcUserDao;
import entity.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/admin"})
public class AdminPageServlet extends HttpServlet {
    private JdbcUserDao jdbcUserDao;

    @Override
    public void init() {
        jdbcUserDao = (JdbcUserDao) getServletContext().getAttribute("jdbcUserDao");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String currentLogin = (String) request.getSession().getAttribute("login");
        User user = jdbcUserDao.findByLogin(currentLogin);
        if (user.getLogin() != null) {
            getServletContext().getRequestDispatcher("/admin-page.jsp").forward(request, response);
        } else {
            response.sendRedirect("/logout");
        }
    }
}

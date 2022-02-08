package app.servlets;

import dao.impl.JdbcUserDao;
import entity.User;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/admin/delete-user"})
public class DeleteUserServlet extends HttpServlet {
    private JdbcUserDao jdbcUserDao;

    @Override
    public void init() {
        jdbcUserDao = (JdbcUserDao) getServletContext().getAttribute("jdbcUserDao");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String deleteLogin = request.getParameter("deleteLogin");
        String currentLogin = (String) request.getSession().getAttribute("login");
        jdbcUserDao.remove(jdbcUserDao.findByLogin(deleteLogin));
        User currentUser = jdbcUserDao.findByLogin(currentLogin);
        request.setAttribute("currentUser", currentUser);
        response.sendRedirect("/admin");
    }
}

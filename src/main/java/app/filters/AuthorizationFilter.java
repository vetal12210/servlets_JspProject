package app.filters;

import entity.Role;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


@WebFilter(filterName = "AuthorizationFilter", urlPatterns = {"/admin/*", "/user/*"})
public class AuthorizationFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse res = (HttpServletResponse) servletResponse;

        HttpSession session = req.getSession();
        Role role = (Role) session.getAttribute("role");
        boolean isAdmin = role.getName().equals("admin");
        boolean isUser = role.getName().equals("user");
        String servletPath = req.getServletPath();

        if (isAdmin && servletPath.contains("/admin")) {
            filterChain.doFilter(req, res);
        } else if (isUser && servletPath.contains("/user")) {
            filterChain.doFilter(req, res);
        } else {
            req.getRequestDispatcher("/login").forward(req, res);
        }

    }
}

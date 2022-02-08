package app.tags;

import dao.impl.JdbcRoleDao;
import dao.impl.JdbcUserDao;
import entity.Role;
import entity.User;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;

public class AdminPageTag implements Tag {
    private PageContext pageContext;
    private JdbcUserDao jdbcUserDao;
    private JdbcRoleDao jdbcRoleDao;

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        jdbcUserDao = (JdbcUserDao) pageContext.getServletContext().getAttribute("jdbcUserDao");
        jdbcRoleDao = (JdbcRoleDao) pageContext.getServletContext().getAttribute("jdbcRoleDao");
        try {
            drawUserTable(out);
        } catch (IOException e) {
            throw new JspException(e);
        }
        return EVAL_BODY_INCLUDE;
    }

    @Override
    public void setPageContext(PageContext pageContext) {
        this.pageContext = pageContext;
    }

    @Override
    public void setParent(Tag tag) {
    }

    @Override
    public Tag getParent() {
        return null;
    }

    @Override
    public int doEndTag() {
        return 0;
    }

    @Override
    public void release() {
    }

    private void drawUserTable(JspWriter out) throws IOException {
        List<User> users = jdbcUserDao.findAll();
        out.print(
                "<table class = \"table\" >\n" +
                        "    <colgroup>\n" +
                        "        <col span=\"5\" style=\"background:Khaki\">\n" +
                        "        <col span=\"2\" style=\"background-color:LightCyan\">\n" +
                        "    </colgroup>\n" +
                        "    <thead>\n" +
                        "    <tr>\n" +
                        "        <th>Login</th>\n" +
                        "        <th>First Name</th>\n" +
                        "        <th>Last Name</th>\n" +
                        "        <th>Age</th>\n" +
                        "        <th>Role</th>\n" +
                        "        <th colspan=\"2\">Actions</th>\n" +
                        "    </tr>\n" +
                        "    </thead>\n" +
                        "    <tbody>");
        for (User user : users) {
            String roleNameById = getRoleNameById(user.getRole(), jdbcRoleDao);
            out.print(
                    " <tr>\n" +
                            "<td> " + user.getLogin() + "</td>\n" +
                            "<td> " + user.getFirstName() + "</td>\n" +
                            "<td> " + user.getLastName() + "</td>\n" +
                            "<td> " + Period.between(user.getBirthday().toLocalDate(), LocalDate.now()).getYears() + "</td>\n" +
                            "<td> " + roleNameById + "</td>\n" +
                            "<td>" +
                            "<a href=\"/admin/delete-user?deleteLogin=" + user.getLogin() + "\" onclick=\"return confirm('Are you sure you want to delete user?')\">delete    </a>" +
                            "</td>\n" +
                            "<td>" +
                            "<a href=\"/admin/edit-user?editLogin=" + user.getLogin() + "\">     edit</a>" +
                            "</td>\n" +
                            " </tr>");
        }
        out.print("    </tbody>\n" +
                "</table>");
    }

    private String getRoleNameById(Long id, JdbcRoleDao jdbcRoleDao) {
        Role byId = jdbcRoleDao.findById(id);
        return byId.getName();
    }
}

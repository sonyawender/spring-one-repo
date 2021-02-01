package ru.geekbrains;

import ru.geekbrains.persist.User;
import ru.geekbrains.persist.UserRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/all-users-servlet/*")
public class AllUsersServlet extends HttpServlet {
    private UserRepository userRepository;

    @Override
    public void init() throws ServletException {
        this.userRepository = (UserRepository) getServletContext().getAttribute("userRepository");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().println("<h1>USERS</h1>");
        resp.getWriter().println("<table style=\"width:10%\">");
        resp.getWriter().println("<tr>");
        resp.getWriter().println("<th>User-ID</th>");
        resp.getWriter().println("<th>Username</th>");
        resp.getWriter().println("</tr>");
        for (User user : userRepository.findAll()) {
            resp.getWriter().println("<tr>");
            resp.getWriter().println("<td>" + user.getId() + "</td>");
            resp.getWriter().println("<td>" + user.getUsername() + "</td>");
            resp.getWriter().println("</tr>");
        }
        resp.getWriter().println("</table>");
    }
}

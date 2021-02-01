package ru.geekbrains;

import ru.geekbrains.persist.User;
import ru.geekbrains.persist.UserRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@WebServlet(urlPatterns = "/user/*")
public class UserServlet extends HttpServlet {
    private UserRepository userRepository;
    private final Pattern PATH_PATTERN = Pattern.compile("\\/(\\d+)");

    @Override
    public void init() throws ServletException {
        this.userRepository = (UserRepository) getServletContext().getAttribute("userRepository");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Matcher matcher = PATH_PATTERN.matcher(req.getPathInfo());
        if (!matcher.matches()){
            resp.setStatus(400);
            resp.getWriter().println("<p>Bad request</p>");
            return;
        }
        long id = Long.parseLong(matcher.group(1));
        User user = userRepository.findById(id);
        if (user == null) {
            resp.setStatus(404);
            resp.getWriter().println("<p>User not found</p>");
            return;
        }

        resp.getWriter().println("<h1>User-ID: " + id + "</h1>");
        resp.getWriter().println("<h1>Username: " + user.getUsername() + "</h1>");
    }
}

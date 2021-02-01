package ru.geekbrains;

import ru.geekbrains.persist.UserRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/my-http-servlet/*")
public class MyHttpServlet extends HttpServlet {
    private UserRepository userRepository;

    @Override
    public void init() throws ServletException {
        this.userRepository = (UserRepository) getServletContext().getAttribute("userRepository");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().println("<h1>Hello from Servlet!!!ПРИВЕЕЕЕЕт!!!</h1>");
        resp.getWriter().println("<p>ContextPath: " + req.getContextPath() + "</p>");
        resp.getWriter().println("<p>ServletPat: " + req.getServletPath() + "</p>");
        resp.getWriter().println("<p>PathInfo: " + req.getPathInfo() + "</p>");
        resp.getWriter().println("<p>QueryString: " + req.getQueryString() + "</p>");
        resp.getWriter().println("<p>param1: " + req.getParameter("param1") + "</p>");
        resp.getWriter().println("<p>param2: " + req.getParameter("param2") + "</p>");
    }
}

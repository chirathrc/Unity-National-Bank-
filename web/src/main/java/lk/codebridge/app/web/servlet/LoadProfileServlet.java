package lk.codebridge.app.web.servlet;

import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lk.codebridge.app.core.model.User;
import lk.codebridge.app.core.service.UserService;

import java.io.IOException;

@WebServlet("/user/loadProfile")
public class LoadProfileServlet extends HttpServlet {

    @EJB
    private UserService userService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String username = req.getUserPrincipal().getName();

        User user = userService.getUser(username);
        req.setAttribute("user", user);

        req.setAttribute("city", user.getCity());

        req.getRequestDispatcher("/user/profile.jsp").forward(req, resp);
    }
}

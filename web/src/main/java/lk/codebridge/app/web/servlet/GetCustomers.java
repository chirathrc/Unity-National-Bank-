package lk.codebridge.app.web.servlet;

import com.google.gson.Gson;
import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jdk.jfr.ContentType;
import lk.codebridge.app.core.dto.Response;
import lk.codebridge.app.core.dto.UserDTO;
import lk.codebridge.app.core.model.User;
import lk.codebridge.app.core.service.UserService;

import java.io.IOException;

@WebServlet("/admin/getUser")
public class GetCustomers extends HttpServlet {

    @EJB
    private UserService userService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        Response response = new Response();
        Gson gson = new Gson();

        String nic = req.getParameter("nic");

        UserDTO user = userService.getUserFromNic(nic);

        GetSingleCustomerDetails.giveUser(resp, response, gson, user);

    }
}

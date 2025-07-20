package lk.codebridge.app.web.servlet;

import com.google.gson.Gson;
import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lk.codebridge.app.core.dto.Response;
import lk.codebridge.app.core.dto.UserDTO;
import lk.codebridge.app.core.service.UserService;

import java.io.IOException;

@WebServlet("/user/getDetails")
public class GetSingleCustomerDetails extends HttpServlet {

    @EJB
    private UserService userService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String username  = req.getUserPrincipal().getName();


        Response response = new Response();
        Gson gson = new Gson();
        UserDTO user = userService.getUserDtoFromUsername(username);


        try {
            giveUser(resp, response, gson, user);
        }catch (Exception e) {
            e.printStackTrace();
        }


    }

    static void giveUser(HttpServletResponse resp, Response response, Gson gson, UserDTO user) throws IOException {
        if (user != null) {

            System.out.println(user);

            response.setSuccess(true);
            response.setMessage(user);
        }else {
            response.setSuccess(false);
            response.setMessage("User not found");
        }

        resp.setContentType("application/json");
        resp.getWriter().write(gson.toJson(response));
    }
}

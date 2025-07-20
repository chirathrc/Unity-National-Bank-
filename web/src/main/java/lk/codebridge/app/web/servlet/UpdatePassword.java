package lk.codebridge.app.web.servlet;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lk.codebridge.app.core.dto.Response;
import lk.codebridge.app.core.model.User;
import lk.codebridge.app.core.service.UserService;
import lk.codebridge.app.core.util.EncryptPassword;

import java.io.IOException;

@WebServlet("/user/updatePassword")
public class UpdatePassword extends HttpServlet {

    @EJB
    private UserService userService;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        Response res = new Response();
        Gson gson = new Gson();
        resp.setContentType("application/json");

        JsonObject json = gson.fromJson(req.getReader(), JsonObject.class);

        String Old_Password = json.get("oldPassword").getAsString();
        String New_Password = json.get("newPassword").getAsString();

        String username = req.getUserPrincipal().getName();

        User user = userService.getUser(username);

        String passwordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,}$";

        System.out.println(user.getPassword());
        System.out.println(Old_Password);

        if (!EncryptPassword.checkPassword(Old_Password,user.getPassword())) {
            res.setMessage("Old Password does not match");
            res.setSuccess(false);
            resp.getWriter().write(gson.toJson(res));
            return;
        }

        if (!New_Password.matches(passwordRegex)) {
            res.setMessage("New password must be at least 8 characters long and include uppercase, lowercase, digit, and special character.");
            res.setSuccess(false);
            resp.getWriter().write(gson.toJson(res));
            return;
        }

        user.setPassword(EncryptPassword.hashPassword(New_Password));

        if (!userService.UpdatePassword(user)) {
            res.setMessage("Something Went Wrong in UpdatePassword.");
            res.setSuccess(false);
            resp.getWriter().write(gson.toJson(res));
            return;
        }

        res.setSuccess(true);
        res.setMessage("Password Updated Successfully");

        resp.getWriter().write(gson.toJson(res));

    }
}

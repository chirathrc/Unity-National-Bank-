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
import lk.codebridge.app.core.dto.UserDTO;
import lk.codebridge.app.core.service.UserService;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;


@WebServlet("/admin/allCustomerManagement")
public class AllCustomerManagement extends HttpServlet {

    @EJB
    private UserService userService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Gson gson = new Gson();

        List<UserDTO> userDTOS = userService.getAllUsers();

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("count", userDTOS.size());


        int todayRegisteredUsers = 0;
        int thisMonthRegisteredUsers = 0;
        for (UserDTO userDTO : userDTOS) {

            LocalDate regDate = userDTO.getRegTime();

            if (regDate.isEqual(LocalDate.now())) {
                todayRegisteredUsers++;
            }

            if (regDate.getMonth() == LocalDate.now().getMonth() && regDate.getYear() == LocalDate.now().getYear()) {
                thisMonthRegisteredUsers++;
            }

        }

        jsonObject.addProperty("todayRegisteredUsers", todayRegisteredUsers);
        jsonObject.addProperty("thisMonthRegisteredUsers", thisMonthRegisteredUsers);
        jsonObject.add("users", gson.toJsonTree(userDTOS));

        resp.setContentType("application/json");
        resp.getWriter().write(gson.toJson(new Response(true, jsonObject)));

    }
}

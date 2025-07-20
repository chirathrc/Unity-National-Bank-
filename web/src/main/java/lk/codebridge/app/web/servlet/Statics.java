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
import lk.codebridge.app.core.model.Account;
import lk.codebridge.app.core.model.AccountType;
import lk.codebridge.app.core.model.User;
import lk.codebridge.app.core.service.BankAccountService;
import lk.codebridge.app.core.service.TransferManagementService;
import lk.codebridge.app.core.service.UserService;

import java.io.IOException;
import java.util.List;

@WebServlet("/admin/statics")
public class Statics extends HttpServlet {

    @EJB
    private BankAccountService bankAccountService;

    @EJB
    private UserService userService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Gson gson = new Gson();
        Response response = new Response();

        try {
            List<Account> accounts = bankAccountService.getAccounts();

            int fd = 0;
            int savings = 0;
            double assets = 0;

            if (accounts != null && !accounts.isEmpty()) {
                for (Account a : accounts) {
                    if (a.getAccountType() == AccountType.Savings) {
                        savings++;
                    }
                    if (a.getAccountType() == AccountType.Fixed_Deposit) {
                        fd++;
                    }
                    assets += a.getBalance();
                }
            }

            List<UserDTO> userDTOS = userService.getAllUsers();
            int activeUserCount = (userDTOS != null) ? userDTOS.size() : 0;

            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("fdAccounts", fd);
            jsonObject.addProperty("activeAccounts", savings);
            jsonObject.addProperty("totalAssets", assets);
            jsonObject.addProperty("totalCustomers", activeUserCount);

            response.setSuccess(true);
            response.setMessage(jsonObject); // already a JsonElement

        } catch (Exception e) {
            e.printStackTrace(); // Optional: log this instead in production
//            req.getSession().invalidate();
            response.setSuccess(false);
            response.setMessage("Failed to load statistics: " + e.getMessage());
        }

        resp.setContentType("application/json");
        resp.getWriter().write(gson.toJson(response));
    }
}

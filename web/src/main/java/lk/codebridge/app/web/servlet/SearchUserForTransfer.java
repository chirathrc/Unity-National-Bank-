package lk.codebridge.app.web.servlet;

import com.google.gson.Gson;
import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lk.codebridge.app.core.dto.Response;
import lk.codebridge.app.core.model.Account;
import lk.codebridge.app.core.service.TransferManagementService;

import java.io.IOException;

@WebServlet("/user/getUserForTransfer")
public class SearchUserForTransfer extends HttpServlet {


    @EJB
    private TransferManagementService transferManagementService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {

            String accountNo = req.getParameter("accountNo");
            Response response = new Response();

            Gson gson = new Gson();
            resp.setContentType("application/json");

            Account account = transferManagementService.getAccountFromUser(req.getUserPrincipal().getName());

            if (account != null) {
                if (account.getAccountNumber().equals(accountNo)) {
                    response.setSuccess(Boolean.FALSE);
                    response.setMessage("You cannot transfer money to yourself.");
                    resp.getWriter().write(gson.toJson(response));
                    return;
                }
            }

            if (transferManagementService.getUsernameFromAccount(accountNo) != null) {

                String username = transferManagementService.getUsernameFromAccount(accountNo);
                response.setSuccess(true);
                response.setMessage(username);


            } else {

                response.setSuccess(Boolean.FALSE);
                response.setMessage("Account not found");
//                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }

            resp.getWriter().write(gson.toJson(response));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

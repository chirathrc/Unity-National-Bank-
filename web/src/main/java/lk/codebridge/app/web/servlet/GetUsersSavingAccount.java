package lk.codebridge.app.web.servlet;

import com.google.gson.Gson;
import jakarta.ejb.EJB;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lk.codebridge.app.core.dto.AccountDTO;
import lk.codebridge.app.core.dto.Response;
import lk.codebridge.app.core.model.Account;
import lk.codebridge.app.core.service.TransferManagementService;

import java.io.IOException;

@WebServlet("/user/getSavings")
public class GetUsersSavingAccount extends HttpServlet {

    @EJB
    private TransferManagementService transferManagementService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        try {
            Response response = new Response();
            response.setSuccess(true);

            Account account = transferManagementService.getAccountFromUser(req.getUserPrincipal().getName());

            if (account != null) {

                AccountDTO accountDTO = (transferManagementService.getSavingsAccountFromUser(req.getUserPrincipal().getName()));
                String last4 = accountDTO.getAccountNumber().substring(accountDTO.getAccountNumber().length() - 4);
                req.setAttribute("last4", last4);
                req.setAttribute("acc", accountDTO);

                req.getRequestDispatcher("/WEB-INF/pages/transfer.jsp").forward(req, resp);
            } else {
                resp.sendRedirect(req.getContextPath() + "/404.jsp");

            }
        } catch (ServletException e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } catch (IOException e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }


//
//
//        response.setMessage(accountDTO);
//        System.out.println(accountDTO);
//
//        resp.getWriter().write(new Gson().toJson(response));


    }
}

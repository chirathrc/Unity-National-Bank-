package lk.codebridge.app.web.servlet;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import jakarta.ejb.EJB;
import jakarta.servlet.Servlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lk.codebridge.app.core.dto.AccountDTO;
import lk.codebridge.app.core.dto.Response;
import lk.codebridge.app.core.dto.UserDTO;
import lk.codebridge.app.core.model.Account;
import lk.codebridge.app.core.model.AccountType;
import lk.codebridge.app.core.model.Branches;
import lk.codebridge.app.core.model.Status;
import lk.codebridge.app.core.service.BankAccountService;
import lk.codebridge.app.core.service.UserService;
import lk.codebridge.app.core.util.AccountNumberGenerate;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;

@WebServlet("/admin/account_create")
public class AccountCreate extends HttpServlet {

    @EJB
    private BankAccountService bankAccountService;

    @EJB
    private UserService userService;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Gson gson = new Gson();
        Response response = new Response();

        JsonObject jsonObject = gson.fromJson(req.getReader(), JsonObject.class);

        String customerNIC = jsonObject.get("customerNIC").getAsString();
        String branch = jsonObject.get("branch").getAsString();
        String accountType = jsonObject.get("accountType").getAsString();
        double initialDeposit = jsonObject.get("initialDeposit").getAsDouble();

        UserDTO user = userService.getUserFromNic(customerNIC);

        if (user == null) {

            response.setMessage("Something went wrong in NIC");
            response.setSuccess(Boolean.FALSE);
            resp.getWriter().write(gson.toJson(response));
            return;

        }

        if (!user.getAccounts().isEmpty()) {

            for (AccountDTO account : user.getAccounts()) {

                if(account.getAccountType() == AccountType.valueOf(accountType)){

                    response.setMessage("This user Already have an account of type " + accountType);
                    response.setSuccess(Boolean.FALSE);
                    resp.getWriter().write(gson.toJson(response));
                    return;

                }

            }

        }


//        UserDTO userDTO = userService.getUserFromNic(customerNIC);

        String bankAccountNumber;
        do {
            bankAccountNumber = AccountNumberGenerate.generateAccountNumber();
        } while (bankAccountService.isFreeAccountNumber(bankAccountNumber));


        Account account = new Account();
        account.setAccountNumber(bankAccountNumber);
        account.setAccountType(AccountType.valueOf(accountType));
        account.setBalance(initialDeposit);
        account.setBranch(Branches.valueOf(branch));
        account.setStatus(Status.ACTIVE);
        account.setCreateDate(LocalDate.now());

        bankAccountService.addBankAccount(account,user.getId());

        response.setSuccess(true);
        response.setMessage(bankAccountNumber);

        resp.getWriter().write(gson.toJson(response));

    }
}

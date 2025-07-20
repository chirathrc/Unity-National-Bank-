package lk.codebridge.app.web.servlet;

import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lk.codebridge.app.core.model.Account;
import lk.codebridge.app.core.model.AccountType;
import lk.codebridge.app.core.model.InterestFinalizeHistory;
import lk.codebridge.app.core.model.Interest_History;
import lk.codebridge.app.core.service.BankAccountService;
import lk.codebridge.app.core.util.RoundDecimal;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@WebServlet("/user/getInterest")
public class InterestServlet extends HttpServlet {


    @EJB
    private BankAccountService bankAccountService;


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String id = req.getParameter("id");
        if (id == null || id.isEmpty()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Account ID is required.");
            return;
        }

        try {
            int accountId = Integer.parseInt(id);
            Account account = bankAccountService.getAccount(id);

            if (account == null) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Account not found.");
                return;
            }

            List<InterestFinalizeHistory> interestHistory = bankAccountService.getInterestHistory(accountId);

            double totalInterest = 0.0;
            double lastMonthInterest = 0.0;

            LocalDate now = LocalDate.now();
            YearMonth lastMonth = YearMonth.from(now).minusMonths(1); // Get last month (e.g. June if now is July)

            for (InterestFinalizeHistory record : interestHistory) {

                System.out.println("Historyitem :" + interestHistory);

                double amount = record.getAmount();
                totalInterest += amount;

                LocalDate recordDate = record.getDate().toLocalDate();
                YearMonth recordYearMonth = YearMonth.from(recordDate);

                if (recordYearMonth.equals(lastMonth)) {
                    lastMonthInterest += amount;
                }

                LocalDateTime ldt = record.getDate(); // or getDate()
                Date date = Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());
                record.setFormattedDate(date); // ✅ This sets the new Date field


            }

            req.setAttribute("rate", bankAccountService.getRateRate(account.getAccountType()).getInterest());
            req.setAttribute("totalInterest", RoundDecimal.roundToTwoDecimals(totalInterest));
            req.setAttribute("lastMonthInterest", RoundDecimal.roundToTwoDecimals(lastMonthInterest));
            req.setAttribute("interestHistory", interestHistory);
            req.setAttribute("account", account);


            req.getRequestDispatcher("/user/interest.jsp").forward(req, resp);

        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid account ID.");
        }

    }
}

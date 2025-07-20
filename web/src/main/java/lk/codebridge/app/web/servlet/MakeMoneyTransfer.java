package lk.codebridge.app.web.servlet;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lk.codebridge.app.core.model.Account;
import lk.codebridge.app.core.model.TransferType;
import lk.codebridge.app.core.service.TransferManagementService;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;
import java.util.Objects;

@WebServlet("/user/transfer")
public class MakeMoneyTransfer extends HttpServlet {

    @EJB
    private TransferManagementService transferManagementService;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Gson gson = new Gson();
        JsonObject json = gson.fromJson(req.getReader(), JsonObject.class);

        String toAccountNumber = json.get("to").getAsString();
        double amount = json.get("amount").getAsDouble();
//        String fromAccountNumber = json.get("from").getAsString();
        String reference = json.get("reference").getAsString() == null ? "N/A" : json.get("reference").getAsString();

        Account account = transferManagementService.getAccountFromUser(req.getUserPrincipal().getName());

        if (account.getBalance() < amount) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        if (Objects.equals(json.get("timing").getAsString(), "1")) {


            try {
                transferManagementService.transferExecute(account.getAccountNumber(), toAccountNumber, amount, req.getUserPrincipal().getName(), TransferType.INSTANT, reference);
            } catch (Exception e) {
                e.printStackTrace();
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }


        } else if (Objects.equals(json.get("timing").getAsString(), "2")) {

            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);

        } else {
            // Example JSON string: "Fri, Jul 18, 09:00 AM"
            String dateString = json.get("timing").getAsString();

            int currentYear = LocalDate.now().getYear();
            String fullDateString = dateString + " " + currentYear;

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE, MMM dd, hh:mm a yyyy");

            try {
                LocalDateTime dateTime = LocalDateTime.parse(fullDateString, formatter);

                LocalDateTime now = LocalDateTime.now();

//                 Check if the parsed time is at least 10 minutes in the future
                if (dateTime.isBefore(now.plusMinutes(10))) {
                    System.out.println("Time must be at least 10 minutes in the future.");
                    resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    return;
                }

                System.out.println("Parsed DateTime: " + dateTime);

                transferManagementService.scheduleTransfer(account.getAccountNumber(), toAccountNumber, amount, req.getUserPrincipal().getName(), TransferType.SCHEDULED, reference, dateTime);

            } catch (DateTimeParseException e) {
                System.out.println("Error parsing date: " + e.getMessage());
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            } catch (Exception e) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                throw new RuntimeException(e);
            }
        }


    }
}

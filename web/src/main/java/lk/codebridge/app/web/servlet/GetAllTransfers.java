package lk.codebridge.app.web.servlet;

import com.google.gson.Gson;
import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lk.codebridge.app.core.dto.MergedTransfer;
import lk.codebridge.app.core.dto.Response;
import lk.codebridge.app.core.model.ScheduledTransfer;
import lk.codebridge.app.core.model.Transfer;
import lk.codebridge.app.core.model.TransferStatus;
import lk.codebridge.app.core.model.TransferType;
import lk.codebridge.app.core.service.TransferManagementService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@WebServlet("/user/getHistory")
public class GetAllTransfers extends HttpServlet {


    @EJB
    private TransferManagementService transferManagementService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String username = req.getUserPrincipal().getName();

        Response response = new Response();
        List<MergedTransfer> allTransfers = new ArrayList<>();

        try {

            List<Transfer> transfers = transferManagementService.getTransfers(username);
            List<ScheduledTransfer> scheduledTransfers = transferManagementService.getScheduledTransfers(username);

            for (Transfer transfer : transfers) {

                MergedTransfer mergedTransfer = new MergedTransfer();
                mergedTransfer.setId(transfer.getId());
                mergedTransfer.setSchedule(false);
                mergedTransfer.setType(transfer.getType().toString());
                mergedTransfer.setDateTime(transfer.getTime());
                mergedTransfer.setAmount(transfer.getAmount());
                mergedTransfer.setRemark(transfer.getRemark());
                mergedTransfer.setFromTransfer(transfer.getAccountFrom().getUser().getUsername().equals(username));
                mergedTransfer.setStatus(transfer.getStatus());

                allTransfers.add(mergedTransfer);
            }

            for (ScheduledTransfer transfer : scheduledTransfers) {

                if (transfer.getStatus() != TransferStatus.COMPLETED) {

                    MergedTransfer mergedTransfer = new MergedTransfer();
                    mergedTransfer.setId(transfer.getId());
                    mergedTransfer.setType(TransferType.SCHEDULED.toString());

                    if (transfer.getStatus() == TransferStatus.PENDING) {
                        mergedTransfer.setSchedule(true);
                        mergedTransfer.setRegisterScheduleTime(transfer.getScheduledTime());
                        mergedTransfer.setDateTime(transfer.getAddTime());
                    } else {
                        mergedTransfer.setDateTime(transfer.getScheduledTime());
                        mergedTransfer.setSchedule(false);
                    }

                    mergedTransfer.setAmount(transfer.getAmount());
                    mergedTransfer.setRemark(transfer.getRemark());
                    mergedTransfer.setStatus(transfer.getStatus());
                    mergedTransfer.setFromTransfer(true);

                    allTransfers.add(mergedTransfer);

                }
            }

//            allTransfers.sort(Comparator.comparing(MergedTransfer::getDateTime));
            allTransfers.sort(Comparator.comparing(MergedTransfer::getDateTime).reversed());


//            for (MergedTransfer t : allTransfers) {
//                System.out.println(t);
//            }

            response.setSuccess(true);
            response.setMessage(allTransfers);

        } catch (Exception e) {
            response.setSuccess(false);
            response.setMessage(e.getMessage());
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }

        resp.getWriter().write(new Gson().toJson(response));

    }
}

package lk.codebridge.app.ejb.beans;

import jakarta.ejb.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.persistence.PersistenceContext;
import lk.codebridge.app.core.model.ScheduledTransfer;
import lk.codebridge.app.core.model.TransferStatus;
import lk.codebridge.app.core.service.ScheduledTransferExecutorService;


import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Singleton
@Startup
public class ScheduledTransferExecutorBean implements ScheduledTransferExecutorService {

    @PersistenceContext
    private EntityManager em;

    @EJB
    private ScheduledTransferHelperBean helper;


    @Schedule(minute = "*", hour = "*", persistent = true)
    @Override
    public void checkAndExecuteScheduledTransfers() {

        List<ScheduledTransfer> transfers = em.createNamedQuery("Schedule.getTransfers", ScheduledTransfer.class)
                .setParameter("now", LocalDateTime.now())
                .setParameter("status", TransferStatus.PENDING)
                .getResultList();

        ExecutorService executor = Executors.newFixedThreadPool(10); // 10 threads; tune this as needed

        for (ScheduledTransfer transfer : transfers) {
            executor.submit(() -> {
                try {
                    helper.processTransfer(transfer);
                } catch (Exception e) {
                    System.err.println("Error during transfer: " + transfer.getId());
                    e.printStackTrace();
                }
            });
        }

        executor.shutdown();
    }
}
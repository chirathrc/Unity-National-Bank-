//package lk.codebridge.app.ejb.beans;
//
//import jakarta.annotation.Resource;
//import jakarta.ejb.EJB;
//import jakarta.ejb.Stateless;
//import jakarta.ejb.TransactionAttribute;
//import jakarta.ejb.TransactionAttributeType;
//import jakarta.inject.Inject;
//import jakarta.persistence.EntityManager;
//import jakarta.persistence.LockModeType;
//import jakarta.persistence.PersistenceContext;
//import jakarta.transaction.UserTransaction;
//import lk.codebridge.app.core.exceptions.TransferException;
//import lk.codebridge.app.core.model.*;
//import lk.codebridge.app.core.service.BankAccountService;
//import lk.codebridge.app.core.service.MoneyTransferService;
//import lk.codebridge.app.core.service.TransferManagementService;
//
//@Stateless
//public class ScheduledTransferHelperBean {
//
//    @PersistenceContext
//    private EntityManager em;
//
//    @EJB
//    private BankAccountService bankAccountService;
//
//    @EJB
//    private SafeUpdateBean safeUpdateBean;
//
//    @EJB
//    private MoneyTransferService moneyTransferService;
//
//    @Resource
//    private UserTransaction utx;
//
//    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
//    // Starts a new independent transaction for this method
//    public void processTransfer(ScheduledTransfer transfer) throws TransferException {
//        try {
//            // Locking the transfer entity to prevent concurrent modification
//            ScheduledTransfer lockedTransfer = em.find(ScheduledTransfer.class, transfer.getId());
//            if (lockedTransfer == null || lockedTransfer.getStatus() != TransferStatus.PENDING) {
//                return; // No transaction rollback here since this is a normal flow exit
//            }
//
//            String username = transfer.getFromAccount().getUser().getUsername();
//
//            Account sender = bankAccountService.getAccountFromUser_Type(username, AccountType.Savings);
//            Account receiver = bankAccountService.getAccountFromAccountNumber_Type(
//                    transfer.getToAccount().getAccountNumber(), AccountType.Savings
//            );
//
//            if (sender.getBalance() < transfer.getAmount()) {
//                throw new TransferException("Insufficient funds.");
//            }
//
//            moneyTransferService.debit(transfer.getAmount(), username);
//            moneyTransferService.credit(transfer.getAmount(), receiver.getAccountNumber(), TransferType.SCHEDULED, username, transfer.getRemark());
//
//            transfer.setStatus(TransferStatus.COMPLETED);
//            em.merge(transfer); // Changes will be committed only if no exception occurs
//
//        } catch (Exception e) {
//            // In case of any failure, mark the transfer as FAILED
//            markTransferAsFailedSafely(transfer.getId());
//            throw new TransferException("Transfer failed: " + e.getMessage());
//        }
//    }
//
//    private void markTransferAsFailedSafely(int transferId) {
//
//        System.out.println(transferId);
//        safeUpdateBean.markTransferAsFailed(transferId);
//    }
//}
//
//
package lk.codebridge.app.ejb.beans;

import jakarta.annotation.Resource;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionManagement;
import jakarta.ejb.TransactionManagementType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.UserTransaction;
import lk.codebridge.app.core.exceptions.TransferException;
import lk.codebridge.app.core.model.*;
import lk.codebridge.app.core.service.BankAccountService;
import lk.codebridge.app.core.service.MoneyTransferService;

@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class ScheduledTransferHelperBean {

    @PersistenceContext
    private EntityManager em;

    @EJB
    private BankAccountService bankAccountService;

    @EJB
    private SafeUpdateBean safeUpdateBean;

    @EJB
    private MoneyTransferService moneyTransferService;

    @Resource
    private UserTransaction utx;

    public void processTransfer(ScheduledTransfer transfer) throws TransferException {
        try {
            utx.begin(); // Start manual transaction

            ScheduledTransfer lockedTransfer = em.find(ScheduledTransfer.class, transfer.getId());
            if (lockedTransfer == null || lockedTransfer.getStatus() != TransferStatus.PENDING) {
                utx.commit(); // Nothing to do, commit and exit
                return;
            }

            String username = transfer.getFromAccount().getUser().getUsername();

            Account sender = bankAccountService.getAccountFromUser_Type(username, AccountType.Savings);
            Account receiver = bankAccountService.getAccountFromAccountNumber_Type(
                    transfer.getToAccount().getAccountNumber(), AccountType.Savings
            );

            if (sender.getBalance() < transfer.getAmount()) {
                throw new TransferException("Insufficient funds.");
            }

            moneyTransferService.debit(transfer.getAmount(), username);
            moneyTransferService.credit(transfer.getAmount(), receiver.getAccountNumber(), TransferType.SCHEDULED, username, transfer.getRemark());

            transfer.setStatus(TransferStatus.COMPLETED);
            em.merge(transfer);

            utx.commit(); // Commit only after successful operations
        } catch (Exception e) {
            try {
                utx.rollback(); // Roll back transaction on failure
            } catch (Exception rollbackEx) {
                rollbackEx.printStackTrace();
            }

            // Handle marking as failed in separate transactional context
            markTransferAsFailedSafely(transfer.getId());

            throw new TransferException("Transfer failed: " + e.getMessage());
        }
    }

    private void markTransferAsFailedSafely(int transferId) {
        System.out.println(transferId);
        safeUpdateBean.markTransferAsFailed(transferId);
    }
}

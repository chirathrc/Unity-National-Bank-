package lk.codebridge.app.ejb.beans;

import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lk.codebridge.app.core.exceptions.TransferException;
import lk.codebridge.app.core.model.*;
import lk.codebridge.app.core.service.BankAccountService;
import lk.codebridge.app.core.service.MoneyTransferService;

import java.time.LocalDateTime;

@Stateless
public class MoneyTransferSessionBean implements MoneyTransferService {

    @PersistenceContext
    private EntityManager em;

    @EJB
    private BankAccountService bankAccountService;

    @PermitAll
    @TransactionAttribute(TransactionAttributeType.MANDATORY) // Requires an existing transaction; will throw an exception if none exists
    @Override
    public void credit(Double amount, String toAccountNumber, TransferType transferType, String username, String remark) throws TransferException {
        try {
            Account sender = bankAccountService.getAccountFromUser_Type(username, AccountType.Savings);
            Account receiver = bankAccountService.getAccountFromAccountNumber_Type(toAccountNumber, AccountType.Savings);

            if (sender == null || receiver == null) {
                // Will trigger rollback since TransferException is marked with @ApplicationException(rollback = true)
                throw new TransferException("Account not found during credit operation");
            }

            Transfer transfer = new Transfer();
            transfer.setAmount(amount);
            transfer.setType(transferType);
            transfer.setAccountTo(receiver);
            transfer.setAccountFrom(sender);
            transfer.setRemark(remark);
            transfer.setStatus(TransferStatus.COMPLETED);
            transfer.setTime(LocalDateTime.now());

            em.persist(transfer); // Participates in the existing transaction

            receiver.setBalance(receiver.getBalance() + amount);
            // Update will also be committed or rolled back with the transaction
            em.merge(receiver);

        } catch (Exception e) {
            // On any exception, the transaction will roll back if a TransferException is thrown
            throw new TransferException("Credit failed: " + e.getMessage());
        }
    }

    @PermitAll
    @TransactionAttribute(TransactionAttributeType.MANDATORY)
    @Override
    public void debit(Double amount, String username) throws TransferException {
        try {
            Account sender = bankAccountService.getAccountFromUser_Type(username, AccountType.Savings);
            if (sender == null) throw new TransferException("Sender account not found");

            sender.setBalance(sender.getBalance() - amount);
            em.merge(sender);
        } catch (Exception e) {
            throw new TransferException("Debit failed: " + e.getMessage());
        }
    }
}

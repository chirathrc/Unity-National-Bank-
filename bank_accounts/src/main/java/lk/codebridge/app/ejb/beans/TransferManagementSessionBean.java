package lk.codebridge.app.ejb.beans;

import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.inject.Inject;
import jakarta.interceptor.Interceptors;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import lk.codebridge.app.core.dto.AccountDTO;
import lk.codebridge.app.core.exceptions.TransferException;
import lk.codebridge.app.core.model.*;
import lk.codebridge.app.core.service.BankAccountService;
import lk.codebridge.app.core.service.MoneyTransferService;
import lk.codebridge.app.core.service.TransferManagementService;
import lk.codebridge.app.core.service.UserService;
import lk.codebridge.app.ejb.interceptors.TransferLoggingInterceptor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Stateless
public class TransferManagementSessionBean implements TransferManagementService {

    @PersistenceContext
    private EntityManager entityManager;

    @EJB
    private BankAccountService bankAccountService;

    @EJB
    private MoneyTransferService moneyTransferService;

    @EJB
    private UserService userService;

    @RolesAllowed({"USER", "ADMIN"})
    @Override
    public AccountDTO getSavingsAccountFromUser(String username) {

        Account account = entityManager.createNamedQuery("Account.getFromUser&Type", Account.class).setParameter("username", username).setParameter("type", AccountType.Savings).getSingleResult();
        return new AccountDTO(account);

    }

    @RolesAllowed({"USER", "ADMIN"})
    @Override
    public String getUsernameFromAccount(String accountNumber) {

        String user;
        try {
            user = entityManager
                    .createNamedQuery("Account.getUserFromAccount", String.class)
                    .setParameter("ac_number", accountNumber)
                    .setParameter("ac_type", AccountType.Savings)
                    .getSingleResult();
        } catch (NoResultException e) {
            user = null;
        }

        return user;
    }

    @RolesAllowed("USER")
    @Interceptors({TransferLoggingInterceptor.class})
    @Override
    public void scheduleTransfer(String fromAcc, String toAcc, double amount, String username, TransferType type, String remark, LocalDateTime executeAt) throws Exception {

        Account sender = bankAccountService.getAccountFromUser_Type(username, AccountType.Savings);
        Account receiver = bankAccountService.getAccountFromAccountNumber_Type(toAcc, AccountType.Savings);

        ScheduledTransfer transfer = new ScheduledTransfer();
        transfer.setAmount(amount);
        transfer.setStatus(TransferStatus.PENDING);
        transfer.setRemark(remark);
        transfer.setScheduledTime(executeAt);
        transfer.setFromAccount(sender);
        transfer.setToAccount(receiver);
        transfer.setAddTime(LocalDateTime.now());

        entityManager.persist(transfer);
    }

    @RolesAllowed("USER")
    @Interceptors({TransferLoggingInterceptor.class})
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    @Override
    public void transferExecute(String fromAccountNumber, String toAccountNumber, double amount, String username, TransferType type, String remark) throws Exception {

        Account sender = bankAccountService.getAccountFromUser_Type(username, AccountType.Savings);
        if (sender == null || !sender.getAccountNumber().equals(fromAccountNumber)) {
            throw new TransferException("Sender account not found: " + fromAccountNumber);
        }

        if (amount <= 0) {
            throw new TransferException("Amount must be greater than zero");
        }

        if (amount > sender.getBalance()) {
            throw new TransferException("Insufficient funds");
        }

        Account receiver = bankAccountService.getAccountFromAccountNumber_Type(toAccountNumber, AccountType.Savings);
        if (receiver == null) {
            throw new TransferException("Receiver account not found: " + toAccountNumber);
        }

        System.out.println("Transferring from " + fromAccountNumber + " to " + toAccountNumber);

        moneyTransferService.debit(amount, username); // Can throw
        moneyTransferService.credit(amount, toAccountNumber, type, username, remark); // Can throw
    }

    @RolesAllowed("USER")
    @Override
    public List<Transfer> getTransfers(String username) {

        User user = userService.getUser(username);
        return entityManager.createNamedQuery("Transfer.getAllForUser", Transfer.class).setParameter("userFrom", user).setParameter("userTo", user).getResultList();

    }

    @RolesAllowed("USER")
    @Override
    public List<ScheduledTransfer> getScheduledTransfers(String username) {

        User user = userService.getUser(username);
        return entityManager.createNamedQuery("Schedule.getAllForUser", ScheduledTransfer.class).setParameter("user", user).getResultList();

    }

    @RolesAllowed({"USER", "ADMIN"})
    @Override
    public Account getAccountFromUser(String username) {
        try {
            return entityManager.createNamedQuery("Account.getFromUser&Type", Account.class)
                    .setParameter("username", username)
                    .setParameter("type", AccountType.Savings)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

}

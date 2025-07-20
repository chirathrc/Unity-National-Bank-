package lk.codebridge.app.core.service;

import jakarta.ejb.Remote;
import lk.codebridge.app.core.dto.AccountDTO;
import lk.codebridge.app.core.model.Account;
import lk.codebridge.app.core.model.ScheduledTransfer;
import lk.codebridge.app.core.model.Transfer;
import lk.codebridge.app.core.model.TransferType;

import java.time.LocalDateTime;
import java.util.List;

@Remote
public interface TransferManagementService {

    public AccountDTO getSavingsAccountFromUser(String username);

    public String getUsernameFromAccount(String accountNumber);

    public void scheduleTransfer(String fromAcc, String toAcc, double amount,
                                 String username, TransferType type, String remark,
                                 LocalDateTime executeAt) throws Exception;

    public void transferExecute(String fromAccountNumber, String toAccountNumber, double amount, String username, TransferType type, String remark) throws Exception;


    public List<Transfer> getTransfers(String username);
    public List<ScheduledTransfer> getScheduledTransfers(String username);

    public Account getAccountFromUser(String username);

}

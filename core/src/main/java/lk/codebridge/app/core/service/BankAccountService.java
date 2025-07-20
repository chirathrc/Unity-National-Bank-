package lk.codebridge.app.core.service;

import jakarta.ejb.Remote;
import lk.codebridge.app.core.model.*;

import java.util.List;

@Remote
public interface BankAccountService {

    public List<String> getAccountTypes();
    public List<String> getBranches();
    public List<String> getStatus();

    public Account getAccount(String accountId);

    public List<Account> getAccounts();

    public void addBankAccount(Account account,int userID);

    public boolean isFreeAccountNumber(String accountNumber);

    public Account getAccountFromUser_Type(String username, AccountType type) throws Exception;
    public Account getAccountFromAccountNumber_Type(String accountNumber, AccountType type);

    public List<InterestFinalizeHistory> getInterestHistory(int id);

    public Rate getRateRate(AccountType type);

    public Transfer getTransfer(int id);

}

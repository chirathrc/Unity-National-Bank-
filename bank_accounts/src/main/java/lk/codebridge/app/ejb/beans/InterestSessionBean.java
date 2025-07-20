package lk.codebridge.app.ejb.beans;

import jakarta.annotation.Resource;
import jakarta.ejb.*;
import jakarta.enterprise.concurrent.ManagedExecutorService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lk.codebridge.app.core.model.*;
import lk.codebridge.app.core.service.BankAccountService;
import lk.codebridge.app.core.service.InterestService;
import lk.codebridge.app.core.util.RoundDecimal;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Singleton
@ConcurrencyManagement(ConcurrencyManagementType.CONTAINER)
public class InterestSessionBean implements InterestService {

    @PersistenceContext
    private EntityManager em;

    @EJB
    private BankAccountService bankAccountService;

    @EJB
    private InterestHelperBean interestHelper;

    @Resource
    private ManagedExecutorService executor;

    @Lock(LockType.WRITE)
    @Schedule(hour = "23", minute = "59", second = "0", persistent = true)  // Runs daily at 11:59 PM
    public void calculateDailyInterest() {
        List<Account> accounts = bankAccountService.getAccounts(); // Fetch all bank accounts

        for (Account account : accounts) {
            if (account.getAccountType() == AccountType.Savings) {  // Apply interest only to savings accounts
                Rate rate = bankAccountService.getRateRate(AccountType.Savings);  // Get applicable interest rate
                executor.submit(() -> interestHelper.safeApplyInterest(account, rate));  // Execute interest calculation in a separate thread for performance
            }
        }
    }

    @Lock(LockType.WRITE)
    @Schedule(dayOfMonth = "1", hour = "0", minute = "0", second = "0", persistent = true)  // real used one
    public void calculateFDInterest() {
        List<Account> accounts = bankAccountService.getAccounts();

        for (Account account : accounts) {
            if (account.getAccountType() == AccountType.Fixed_Deposit) {
                Rate rate = bankAccountService.getRateRate(AccountType.Fixed_Deposit);
                executor.submit(() -> interestHelper.safeApplyInterest(account, rate));
            }
        }
    }

    @Lock(LockType.WRITE)
    @Schedule(dayOfMonth = "1", hour = "0", minute = "0", second = "0", persistent = true)  // real used one
    public void finalizeSavingsInterest() {
        List<Account> accounts = bankAccountService.getAccounts();

        for (Account account : accounts) {
            if (account.getAccountType() == AccountType.Savings) {
                Rate rate = bankAccountService.getRateRate(AccountType.Savings);

                if (rate != null) {
                    try {
                        double accrued = RoundDecimal.roundDoubleToTwoDecimals(account.getAccruedInterest());

                        InterestFinalizeHistory interestFinalizeHistory = new InterestFinalizeHistory();
                        interestFinalizeHistory.setAccount(account);
                        interestFinalizeHistory.setAmount(accrued);
                        interestFinalizeHistory.setDate(LocalDateTime.now());
                        interestFinalizeHistory.setRate(rate.getInterest());

                        BigDecimal balance = RoundDecimal.roundToTwoDecimals(account.getBalance());
                        BigDecimal newBalance = RoundDecimal.roundToTwoDecimals(balance.add(BigDecimal.valueOf(accrued)));

                        account.setBalance(newBalance.doubleValue());
                        account.setAccruedInterest(0);

                        em.merge(account);
                        em.persist(interestFinalizeHistory);

                    } catch (Exception e) {
                        System.err.println("[ERROR] Finalizing savings interest failed for account ID " + account.getId());
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
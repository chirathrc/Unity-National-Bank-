package lk.codebridge.app.ejb.beans;

import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.persistence.PersistenceContext;
import lk.codebridge.app.core.model.*;
import lk.codebridge.app.core.exceptions.InterestCalculationException;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static lk.codebridge.app.core.util.RoundDecimal.roundDoubleToTwoDecimals;
import static lk.codebridge.app.core.util.RoundDecimal.roundToTwoDecimals;

@Stateless
public class InterestHelperBean {

    @PersistenceContext
    private EntityManager em;

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW) // Begins a new independent transaction, separate from any caller
    public void safeApplyInterest(Account account, Rate rate) {
        try {
            // Ensures the entity is managed and locked to prevent concurrent updates
            Account managedAccount = em.merge(account);
            em.lock(managedAccount, LockModeType.PESSIMISTIC_WRITE); // Acquires a database-level lock for safe updates
            applyInterest(account, rate); // This method executes within the same new transaction
        } catch (Exception ex) {
            // If any exception occurs, the transaction will be marked for rollback
            System.err.println("[ERROR] Interest calculation failed for Account ID: " + account.getId());
//            ex.printStackTrace();

            // Throwing this runtime exception causes rollback because it's unchecked
            throw new InterestCalculationException("Transaction rolled back for account ID: " + account.getId(), ex);
        }
    }

    private void applyInterest(Account account, Rate rate) {
        double balance = account.getBalance();
        double accruedInterest = account.getAccruedInterest();
        double interestRate = rate.getInterest();

        double interestAmount;

        if (account.getAccountType() == AccountType.Savings) {
            interestAmount = calculateDailyInterest(balance, interestRate);
            BigDecimal interestInDecimal = roundToTwoDecimals(interestAmount);
            BigDecimal accruedInterestInDecimal = roundToTwoDecimals(accruedInterest);
            BigDecimal newAccrued = roundToTwoDecimals(interestInDecimal.add(accruedInterestInDecimal));
            account.setAccruedInterest(newAccrued.doubleValue());
            interestUpdate(account, interestRate, interestAmount);

        } else if (account.getAccountType() == AccountType.Fixed_Deposit) {
            interestAmount = calculateMonthlyInterest(balance, interestRate);
            double newBalance = roundDoubleToTwoDecimals(balance + interestAmount);
            account.setBalance(newBalance);
            interestUpdate(account, interestRate, interestAmount);

            InterestFinalizeHistory interestFinalizeHistory = new InterestFinalizeHistory();
            interestFinalizeHistory.setAccount(account);
            interestFinalizeHistory.setAmount(interestAmount);
            interestFinalizeHistory.setDate(LocalDateTime.now());
            interestFinalizeHistory.setRate(rate.getInterest());

            em.persist(interestFinalizeHistory);
        }
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    private void interestUpdate(Account account, double interestRate, double interestAmount) {
        em.merge(account);

        Interest_History ih = new Interest_History();
        ih.setAccount(account);
        ih.setAmount(interestAmount);
        ih.setRate(interestRate);
        ih.setTime(LocalDateTime.now());
        em.persist(ih);
    }

    private double calculateDailyInterest(double balance, double annualRate) {
        double interest = balance / 100.0;
        return roundDoubleToTwoDecimals(interest * annualRate / 365);
    }

    private double calculateMonthlyInterest(double balance, double annualRate) {
        double monthlyInterest = balance * (annualRate / 100.0) / 12;
        return roundDoubleToTwoDecimals(monthlyInterest);
    }
}
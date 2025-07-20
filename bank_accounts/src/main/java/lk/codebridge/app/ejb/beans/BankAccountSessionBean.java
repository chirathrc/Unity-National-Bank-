package lk.codebridge.app.ejb.beans;

import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import lk.codebridge.app.core.model.*;
import lk.codebridge.app.core.service.BankAccountService;
import lk.codebridge.app.core.service.UserService;

import javax.swing.text.html.parser.Entity;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class BankAccountSessionBean implements BankAccountService {

    @PersistenceContext
    private EntityManager em;

    @EJB
    private UserService userService;

    @RolesAllowed("ADMIN")
    @Override
    public List<String> getAccountTypes() {
        return Arrays.stream(AccountType.values())
                .filter(type -> type != AccountType.Administrator)
                .map(Enum::name)
                .collect(Collectors.toList());

    }

    @RolesAllowed("ADMIN")
    @Override
    public List<String> getBranches() {
        return Arrays.stream(Branches.values()).map(Enum::name).collect(Collectors.toList());
    }

    @RolesAllowed("ADMIN")
    @Override
    public List<String> getStatus() {
        return Arrays.stream(Status.values()).map(Enum::name).collect(Collectors.toList());
    }

    @RolesAllowed({"USER", "ADMIN"})
    @Override
    public Account getAccount(String accountId) {

        try {
            return em.find(Account.class, Integer.parseInt(accountId));
        } catch (NoResultException e) {
            return null;
        }
    }

    @PermitAll
    @Override
    public List<Account> getAccounts() {
        return em.createNamedQuery("Account.getAll", Account.class).getResultList();
    }

    @RolesAllowed("ADMIN")
    @Override
    public void addBankAccount(Account account, int userID) {

        User userRef = em.getReference(User.class, userID);

        account.setUser(userRef);

        em.persist(account);

    }

    @RolesAllowed("ADMIN")
    @Override
    public boolean isFreeAccountNumber(String accountNumber) {

        List<Account> accountList = em.createNamedQuery("Account.getAll", Account.class).getResultList();

        if (accountList != null && !accountList.isEmpty()) {

            for (Account account : accountList) {
                if (account.getAccountNumber().equals(accountNumber)) {
                    return true;
                }
            }

        }

        return false;

    }

    @PermitAll
    @Override
    public Account getAccountFromUser_Type(String username, AccountType type) throws Exception {
        try {
            return em.createNamedQuery("Account.getFromUser&Type", Account.class)
                    .setParameter("username", username).setParameter("type", AccountType.Savings).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @PermitAll
    @Override
    public Account getAccountFromAccountNumber_Type(String accountNumber, AccountType type) {

        try {
            return em.createNamedQuery("Account.getAccountFromNumber", Account.class)
                    .setParameter("ac_number", accountNumber)
                    .setParameter("ac_type", AccountType.Savings).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @RolesAllowed("USER")
    @Override
    public List<InterestFinalizeHistory> getInterestHistory(int id) {

        return em.createNamedQuery("finalHistory.getHistory", InterestFinalizeHistory.class).setParameter("id", id).getResultList();
    }

    @PermitAll
    @Override
    public Rate getRateRate(AccountType type) {
        try {
            return em.createNamedQuery("Rate.getRate", Rate.class)
                    .setParameter("type", type)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @RolesAllowed("USER")
    @Override
    public Transfer getTransfer(int id) {
        try {
            return em.find(Transfer.class, id);
        } catch (NoResultException e) {
            return null;
        }
    }

}

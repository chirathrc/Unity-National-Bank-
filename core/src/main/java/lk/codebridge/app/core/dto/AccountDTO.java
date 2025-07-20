package lk.codebridge.app.core.dto;

import lk.codebridge.app.core.model.Account;
import lk.codebridge.app.core.model.AccountType;
import lk.codebridge.app.core.model.Branches;
import lk.codebridge.app.core.model.Status;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountDTO implements Serializable {
    private int id;
    private String accountNumber;
    private LocalDate createDate;
    private double balance;
    private AccountType accountType;
    private Status status;
    private Branches branch;
    private int userId;

    // ✅ Constructor to map from Account entity
    public AccountDTO(Account account) {
        this.id = account.getId();
        this.accountNumber = account.getAccountNumber();
        this.createDate = account.getCreateDate();
        this.balance = account.getBalance();
        this.accountType = account.getAccountType();
        this.status = account.getStatus();
        this.branch = account.getBranch();
        this.userId = account.getUser().getId(); // assumes User has getId()
    }
}

package lk.codebridge.app.core.model;

import jakarta.inject.Named;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "account")
@NoArgsConstructor
@AllArgsConstructor
@NamedQueries({
        @NamedQuery(name = "Account.getAll", query = "select a from Account a"),
        @NamedQuery(name = "Account.getFromUser&Type", query = "select a from Account a where a.user.username=:username and a.accountType=:type"),
        @NamedQuery(name = "Account.getUserFromAccount", query = "select a.user.fullName from Account a where a.accountNumber=:ac_number and a.accountType=:ac_type"),
        @NamedQuery(name = "Account.getAccountFromNumber", query = "select a from Account a where a.accountNumber=:ac_number and a.accountType=:ac_type"),
})
public class Account implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "accountID")
    private int id;

    @Column(name = "acccountNumber", length = 45, nullable = false, unique = true)
    private String accountNumber;

    @Column(name = "createDate")
    private LocalDate createDate;

    @Column(nullable = false)
    private double balance;

    private double accruedInterest;

    @Enumerated(EnumType.STRING)
    @Column(name = "accountType", length = 45)
    private AccountType accountType;

    @Enumerated(EnumType.STRING)
    @Column(length = 45)
    private Status status;

    @Enumerated(EnumType.STRING)
    @Column(length = 45)
    private Branches branch;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}

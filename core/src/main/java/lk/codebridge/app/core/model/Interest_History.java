package lk.codebridge.app.core.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "Intrest_History")
@Data
@AllArgsConstructor
@NoArgsConstructor
@NamedQueries({
        @NamedQuery(name = "history.GetAccountHistory", query = "select h from Interest_History h where h.account.id=:id"),
})
public class Interest_History implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private double amount;
    private LocalDateTime time;
    private double rate;

    @ManyToOne
    @JoinColumn(name = "account_accountID")
    private Account account;


}

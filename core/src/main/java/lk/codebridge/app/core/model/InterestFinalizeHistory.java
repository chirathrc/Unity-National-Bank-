package lk.codebridge.app.core.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "interestFinalizeHistory")
@Data
@AllArgsConstructor
@NoArgsConstructor
@NamedQueries({
        @NamedQuery(name = "finalHistory.getHistory", query = "select his from InterestFinalizeHistory his where his.account.id=:id ORDER BY his.date DESC"),
})
public class InterestFinalizeHistory implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private double amount;

    private LocalDateTime date;

    @ManyToOne
    @JoinColumn(name = "account_accountID")
    private Account account;

    private double rate;

    @Transient
    private Date formattedDate;

}

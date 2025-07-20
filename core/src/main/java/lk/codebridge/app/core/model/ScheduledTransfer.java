package lk.codebridge.app.core.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@NamedQueries({
        @NamedQuery(name = "Schedule.getTransfers", query = "SELECT t FROM ScheduledTransfer t WHERE t.status = :status AND t.scheduledTime <= :now"),
        @NamedQuery(name = "Schedule.getAllForUser", query = "select s FROM ScheduledTransfer s where s.fromAccount.user=:user"),
})
@Entity
@Table(name = "scheduled_transfer")
public class ScheduledTransfer implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private double amount;
    private LocalDateTime scheduledTime;

    @Column(columnDefinition = "TEXT")
    private String remark;

    @ManyToOne
    @JoinColumn(name = "fromAccountNumber")
    private Account fromAccount;

    @ManyToOne
    @JoinColumn(name = "toAccountNumber")
    private Account toAccount;

    @Enumerated(EnumType.STRING)
    private TransferStatus status = TransferStatus.PENDING;

    private LocalDateTime addTime;


}

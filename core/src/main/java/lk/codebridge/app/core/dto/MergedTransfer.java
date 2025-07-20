package lk.codebridge.app.core.dto;

import lk.codebridge.app.core.model.TransferStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MergedTransfer implements Serializable {

    private int id;
    private String type;
    private boolean isSchedule;
    private LocalDateTime dateTime;
    private double amount;
    public boolean isFromTransfer;
    private TransferStatus status;
    private String remark;
    private LocalDateTime registerScheduleTime;
}

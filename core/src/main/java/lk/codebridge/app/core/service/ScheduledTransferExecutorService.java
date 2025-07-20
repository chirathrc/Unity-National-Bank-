package lk.codebridge.app.core.service;

import jakarta.ejb.Remote;
import jakarta.ejb.Timer;

public interface ScheduledTransferExecutorService {

    public void checkAndExecuteScheduledTransfers();

}

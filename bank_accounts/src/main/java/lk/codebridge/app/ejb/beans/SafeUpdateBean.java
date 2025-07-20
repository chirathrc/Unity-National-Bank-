package lk.codebridge.app.ejb.beans;

import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lk.codebridge.app.core.model.ScheduledTransfer;
import lk.codebridge.app.core.model.TransferStatus;

@Stateless
public class SafeUpdateBean {

    @PersistenceContext
    private EntityManager em;

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void markTransferAsFailed(int transferId) {

        System.out.println("Marking transfer failed: " + transferId);
        try {
            ScheduledTransfer transfer = em.find(ScheduledTransfer.class, transferId);
            if (transfer != null) {
                transfer.setStatus(TransferStatus.FAILED);
                em.merge(transfer);
            }
        } catch (Exception e) {
            System.err.println("Failed to mark transfer as FAILURE: " + e.getMessage());
        }
    }
}

package lk.codebridge.app.core.service;

import jakarta.ejb.Remote;
import lk.codebridge.app.core.exceptions.TransferException;
import lk.codebridge.app.core.model.TransferType;

@Remote
public interface MoneyTransferService {

    public void credit(Double amount, String To_accountNumber, TransferType transferType, String username, String remark) throws TransferException;

    public void debit(Double amount, String username)throws TransferException;

}

package lk.codebridge.app.core.exceptions;

import jakarta.ejb.ApplicationException;

@ApplicationException(rollback = true)
public class TransferException extends Exception {
    public TransferException(String message) {
        super(message);
    }

    public TransferException(String message, Throwable cause) {
        super(message, cause);
    }
}




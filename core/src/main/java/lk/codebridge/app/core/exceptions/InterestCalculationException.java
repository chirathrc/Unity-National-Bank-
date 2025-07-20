package lk.codebridge.app.core.exceptions;

import jakarta.ejb.ApplicationException;

@ApplicationException(rollback = true)
public class InterestCalculationException extends RuntimeException {
    public InterestCalculationException(String message, Throwable cause) {
        super(message, cause);
    }
}


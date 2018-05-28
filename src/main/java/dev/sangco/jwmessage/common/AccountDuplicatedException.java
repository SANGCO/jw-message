package dev.sangco.jwmessage.common;

public class AccountDuplicatedException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public AccountDuplicatedException() {
    }

    public AccountDuplicatedException(String message) {
        super(message);
    }

    public AccountDuplicatedException(String message, Throwable cause) {
        super(message, cause);
    }

    public AccountDuplicatedException(Throwable cause) {
        super(cause);
    }

    public AccountDuplicatedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

package dev.sangco.jwmessage.common;

public class RoleDuplicatedException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public RoleDuplicatedException() {
    }

    public RoleDuplicatedException(String message) {
        super(message);
    }

    public RoleDuplicatedException(String message, Throwable cause) {
        super(message, cause);
    }

    public RoleDuplicatedException(Throwable cause) {
        super(cause);
    }

    public RoleDuplicatedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

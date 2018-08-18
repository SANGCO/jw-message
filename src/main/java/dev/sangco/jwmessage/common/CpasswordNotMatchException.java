package dev.sangco.jwmessage.common;

public class CpasswordNotMatchException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public CpasswordNotMatchException() {
    }

    public CpasswordNotMatchException(String message) {
        super(message);
    }

    public CpasswordNotMatchException(String message, Throwable cause) {
        super(message, cause);
    }

    public CpasswordNotMatchException(Throwable cause) {
        super(cause);
    }

    public CpasswordNotMatchException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

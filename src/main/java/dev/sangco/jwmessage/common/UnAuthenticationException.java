package dev.sangco.jwmessage.common;

public class UnAuthenticationException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public UnAuthenticationException() {
    }

    public UnAuthenticationException(String message) {
        super(message);
    }

    public UnAuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnAuthenticationException(Throwable cause) {
        super(cause);
    }

    public UnAuthenticationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

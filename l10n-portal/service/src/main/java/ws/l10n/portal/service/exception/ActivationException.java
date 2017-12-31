package ws.l10n.portal.service.exception;

public class ActivationException extends RuntimeException {
    public ActivationException() {
    }

    public ActivationException(String message) {
        super(message);
    }

    public ActivationException(String message, Throwable cause) {
        super(message, cause);
    }

    public ActivationException(Throwable cause) {
        super(cause);
    }

    public ActivationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
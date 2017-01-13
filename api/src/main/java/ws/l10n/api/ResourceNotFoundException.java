package ws.l10n.api;

/**
 * @author Anton Mokshyn
 */
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException() {
    }

    public ResourceNotFoundException(String message) {
        super(message);
    }
}

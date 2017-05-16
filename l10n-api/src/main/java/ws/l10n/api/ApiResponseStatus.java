package ws.l10n.api;

/**
 * @author Sergey Boguckiy
 */
public enum ApiResponseStatus {

    NOT_FOUND(1),
    ACCESS_DENIED(2),
    BAD_PARAMETER(3),
    BAD_HTTP_REQUEST(4),
    MISSING_PARAMETER(5),
    INTERNAL_SERVER_ERROR(6),
    UNAUTHORIZED(7);

    private int code;

    ApiResponseStatus(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}


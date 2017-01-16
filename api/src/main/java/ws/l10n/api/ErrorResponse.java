package ws.l10n.api;

/**
 * @author Serhii Bohutskyi
 */
public class ErrorResponse {

    private int code;
    private String reason;

    public ErrorResponse(ApiResponseStatus status, String reason) {
        this.code = status.getCode();
        this.reason = reason;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}

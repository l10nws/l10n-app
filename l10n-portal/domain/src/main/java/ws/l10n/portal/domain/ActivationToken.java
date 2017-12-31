package ws.l10n.portal.domain;

public class ActivationToken {
    private final Integer userId;
    private final String token;

    public ActivationToken(Integer userId, String token) {
        this.userId = userId;
        this.token = token;
    }

    public Integer getUserId() {
        return userId;
    }

    public String getToken() {
        return token;
    }
}

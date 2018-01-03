package ws.l10n.portal.service.event;

public class PortalUserRegisteredEvent {
    private String activationToken;
    private String email;
    private String appUrl;

    public PortalUserRegisteredEvent(String activationToken, String email, String appUrl) {
        this.activationToken = activationToken;
        this.email = email;
        this.appUrl = appUrl;
    }

    public String getActivationToken() {
        return activationToken;
    }

    public String getEmail() {
        return email;
    }

    public String getAppUrl() {
        return appUrl;
    }
}

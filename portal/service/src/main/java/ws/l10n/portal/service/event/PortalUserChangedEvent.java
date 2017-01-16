package ws.l10n.portal.service.event;

import ws.l10n.portal.config.meta.UserMeta;

import java.util.List;

/**
 * @author Serhii Bohutskyi
 */
public class PortalUserChangedEvent {

    private final List<UserMeta> users;

    public PortalUserChangedEvent(List<UserMeta> userMeta) {
        this.users = userMeta;
    }

    public List<UserMeta> getUserMeta() {
        return users;
    }
}

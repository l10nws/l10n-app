package ws.l10n.portal.domain.view;

import ws.l10n.portal.domain.Permission;

/**
 * @author Anton Mokshyn
 */
public class UserPermissionView {

    private final Integer userId;
    private final Integer projectId;
    private final String email;
    private final Permission permission;

    public UserPermissionView(Integer userId, Integer projectId, String email, Permission permission) {
        this.userId = userId;
        this.projectId = projectId;
        this.email = email;
        this.permission = permission;
    }

    public Integer getUserId() {
        return userId;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public String getEmail() {
        return email;
    }

    public Permission getPermission() {
        return permission;
    }

    /**
     * serialization artifact.
     */
    private UserPermissionView() {
        this.userId = null;
        this.projectId = null;
        this.email = null;
        this.permission = null;
    }
}

package ws.l10n.portal.domain;

/**
 * @author Anton Mokshyn
 */
public class UserPermission {

    private final Integer userId;
    private final Integer projectId;
    private final Permission permission;

    public UserPermission(Integer userId, Integer projectId, Permission permission) {
        this.userId = userId;
        this.projectId = projectId;
        this.permission = permission;
    }

    public Integer getUserId() {
        return userId;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public Permission getPermission() {
        return permission;
    }

    /**
     * serialization artifact.
     */
    private UserPermission() {
        this.userId = null;
        this.projectId = null;
        this.permission = null;
    }
}

package ws.l10n.portal.repository;

import ws.l10n.portal.domain.UserPermission;

import java.util.List;

/**
 * @author Anton Mokshyn
 */
public interface PortalUserPermissionRepository {
    UserPermission findByUserIdAndProjectId(Integer userId, Integer projectId);
    List<UserPermission> findByProjectId(Integer projectId);
    void save(UserPermission userPermission);
    void update(UserPermission userPermission);
    void deleteByProjectIdAndUserId(Integer projectId, Integer userId);
    void deleteByProjectId(Integer projectId);
}

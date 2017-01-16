package ws.l10n.portal.ui.authorize;

import org.springframework.beans.factory.annotation.Autowired;
import ws.l10n.portal.domain.Permission;
import ws.l10n.portal.domain.UserPermission;
import ws.l10n.portal.repository.BundleRepository;
import ws.l10n.portal.repository.PortalUserPermissionRepository;
import ws.l10n.portal.repository.ProjectRepository;
import ws.l10n.portal.repository.VersionRepository;
import ws.l10n.portal.ui.security.PortalSecurity;

/**
 * @author Anton Mokshyn.
 */
public class PermissionChecker {

    @Autowired
    private VersionRepository versionRepository;
    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private BundleRepository bundleRepository;

    @Autowired
    private PortalUserPermissionRepository userPermissionRepository;

    public boolean canWriteBundle(Integer bundleId) {
        Integer projectId = bundleRepository.getProjectId(bundleId);
        return projectId != null && canWriteProject(projectId);
    }

    public boolean canReadBundle(Integer bundleId) {
        Integer projectId = bundleRepository.getProjectId(bundleId);
        return projectId != null && canReadProject(projectId);
    }

    public boolean canWriteVersion(Integer versionId) {
        Integer projectId = versionRepository.getProjectId(versionId);
        return projectId != null && canWriteProject(projectId);
    }

    public boolean canReadVersion(Integer versionId) {
        Integer projectId = versionRepository.getProjectId(versionId);
        return projectId != null && canReadProject(projectId);
    }

    public boolean canWriteProject(Integer projectId) {
        return isProjectOwner(projectId) || hasPermission(projectId, Permission.WRITE.toString());
    }

    public boolean canReadProject(Integer projectId) {
        return isProjectOwner(projectId) || userPermissionRepository.findByUserIdAndProjectId(currentUserId(), projectId) != null;
    }

    public boolean hasProject(Integer projectId) {
        UserPermission userPermission = userPermissionRepository
                .findByUserIdAndProjectId(currentUserId(), projectId);
        return userPermission != null || isProjectOwner(projectId);
    }

    public boolean hasPermission(Integer projectId, String permission) {
        UserPermission userPermission = userPermissionRepository
                .findByUserIdAndProjectId(currentUserId(), projectId);
        return userPermission != null && userPermission.getPermission() == Permission.valueOf(permission);
    }


    public boolean isProjectOwner(Integer projectId) {
        return projectRepository.isProjectOwner(projectId, currentUserId());
    }


    private Integer currentUserId() {
        return PortalSecurity.currentUser().getId();
    }

}

package ws.l10n.portal.service;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ws.l10n.portal.domain.MessageBundle;
import ws.l10n.portal.domain.Permission;
import ws.l10n.portal.domain.PortalUser;
import ws.l10n.portal.domain.Project;
import ws.l10n.portal.domain.ProjectBase;
import ws.l10n.portal.domain.UserPermission;
import ws.l10n.portal.domain.view.ProjectView;
import ws.l10n.portal.repository.BundleRepository;
import ws.l10n.portal.repository.PortalUserPermissionRepository;
import ws.l10n.portal.repository.PortalUserRepository;
import ws.l10n.portal.repository.ProjectRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Anton Mokshyn
 */
@Service
@Transactional
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private PortalUserRepository userRepository;

    @Autowired
    private BundleRepository bundleRepository;

    @Autowired
    private BundleService messageBundleService;

    @Autowired
    private PortalUserPermissionRepository userPermissionRepository;

    public ProjectBase create(ProjectBase project) {
        project.setCreationDate(new Date());
        projectRepository.save(project);
        return project;
    }

    public void delete(Integer projectId) {
        userPermissionRepository.deleteByProjectId(projectId);
        List<MessageBundle> messageBundles = bundleRepository.getByProjectId(projectId);
        for (MessageBundle messageBundle : messageBundles) {
            messageBundleService.delete(messageBundle.getId());
        }
        projectRepository.delete(projectId);
    }

    public boolean assignUser(Integer projectId, String email, Permission permission) {
        PortalUser portalUser = userRepository.getByEmail(email);
        if (portalUser == null) {
            return false;
        }
        userPermissionRepository.save(new UserPermission(portalUser.getId(), projectId, permission));

        return true;
    }

    public void unassignUser(Integer projectId, Integer userId) {
        userPermissionRepository.deleteByProjectIdAndUserId(projectId, userId);
    }

    public void setUserPermission(Integer projectId, Integer userId, Permission permission) {
        userPermissionRepository.update(new UserPermission(userId, projectId, permission));
    }

    public List<Project> allProjects(Integer userId) {
        List<Project> result = new ArrayList<>(projectRepository.getProjectsByOwnerId(userId));
        result.addAll(projectRepository.getProjectsByUserId(userId));
        return result;
    }

    public List<Project> listByOwnerIdAndPermissions(Integer ownerId, Permission... permissions) {
        List<Project> result = new ArrayList<>(projectRepository.getProjectsByOwnerId(ownerId));
        result.addAll(projectRepository.getProjectsByUserIdAndPermissions(ownerId, permissions));
        return result;
    }

    public List<ProjectView> allProjectViews(Integer userId) {
        List<ProjectView> result = new ArrayList<>(projectRepository.getProjectViewsByOwnerId(userId));
        result.addAll(projectRepository.getProjectViewsByUserId(userId));
        return new ArrayList<>(Collections2.filter(result, new Predicate<ProjectView>() {
            @Override
            public boolean apply(ProjectView input) {
                return !input.getMessageBundles().isEmpty();
            }
        }));
    }

}

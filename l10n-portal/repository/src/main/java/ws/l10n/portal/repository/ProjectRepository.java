package ws.l10n.portal.repository;

import org.apache.ibatis.annotations.Param;
import ws.l10n.portal.domain.Permission;
import ws.l10n.portal.domain.Project;
import ws.l10n.portal.domain.ProjectBase;
import ws.l10n.portal.domain.view.ProjectView;

import java.util.List;

/**
 * @author Anton Mokshyn
 */
public interface ProjectRepository {
    Integer save(ProjectBase project);

    List<Project> getProjectsByOwnerId(Integer userId);

    List<Project> getProjectsByUserId(Integer userId);

    Project getProjectById(Integer projectId);

    ProjectView getProjectViewByProjectId(Integer projectId);

    List<ProjectView> getProjectViewsByOwnerId(Integer ownerId);

    List<ProjectView> getProjectViewsByUserId(Integer userId);

    ProjectBase get(Integer projectId);

    Integer update(ProjectBase project);

    void delete(Integer id);

    String getProjectName(Integer id);

    List<ProjectBase> getAll();

    boolean isProjectOwner(@Param("projectId") Integer projectId, @Param("userId") Integer userId);

    List<Project> getProjectsByUserIdAndPermissions(@Param("userId") Integer userId, @Param("permissions") Permission... permissions);
}

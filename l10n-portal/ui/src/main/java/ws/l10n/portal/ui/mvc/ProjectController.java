package ws.l10n.portal.ui.mvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import ws.l10n.portal.domain.Permission;
import ws.l10n.portal.domain.PortalUser;
import ws.l10n.portal.domain.ProjectBase;
import ws.l10n.portal.repository.PortalUserPermissionRepository;
import ws.l10n.portal.repository.ProjectRepository;
import ws.l10n.portal.service.ProjectService;
import ws.l10n.portal.ui.security.PortalSecurity;

/**
 * @author Sergey Boguckiy
 */
@Controller
@RequestMapping("/project")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private PortalUserPermissionRepository userPermissionRepository;

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public String all(ModelMap map) {
        map.put("projects", projectService.allProjects(PortalSecurity.currentUser().getId()));
        return "projects";
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    @PreAuthorize("isAuthenticated() and @permissionChecker.isProjectOwner(#projectId)")
    public String edit(@PathVariable("id") Integer projectId, ModelMap map) {
        map.put("project", projectRepository.get(projectId));
        return "edit_project";
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    @PreAuthorize("isAuthenticated()")
    public String createView() {
        return "add_project";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @PreAuthorize("isAuthenticated()")
    public String create(ProjectBase project) {
        project.setOwnerId(PortalSecurity.currentUser().getId());
        projectService.create(project);
        return "redirect:/project/all";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @PreAuthorize("isAuthenticated() and @permissionChecker.isProjectOwner(#project.id)")
    public String update(ProjectBase project) {
        projectRepository.update(project);
        return "redirect:/project/all";
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    @PreAuthorize("isAuthenticated() and @permissionChecker.isProjectOwner(#projectId)")
    @ResponseBody
    public void delete(@PathVariable("id") Integer projectId) {
        projectService.delete(projectId);
    }

    @RequestMapping(value = "/config/{id}", method = RequestMethod.GET)
    @PreAuthorize("isAuthenticated() and @permissionChecker.isProjectOwner(#projectId)")
    public String config(@PathVariable("id") Integer projectId, ModelMap modelMap) {
        modelMap.put("project", projectRepository.get(projectId));
        modelMap.put("owner", PortalSecurity.currentUser());
        modelMap.put("users", userPermissionRepository.findByProjectId(projectId));
        return "project_config";
    }

    @RequestMapping(value = "/assign/{id}", method = RequestMethod.POST)
    @PreAuthorize("isAuthenticated() and @permissionChecker.isProjectOwner(#projectId)")
    public String assignUser(@PathVariable("id") Integer projectId,
                             @RequestParam("email") String email,
                             @RequestParam("permission") Permission permission) {
        PortalUser currentUser = PortalSecurity.currentUser();
        if (currentUser.getEmail().equals(email) || !projectService.assignUser(projectId, email, permission)) {
            return "redirect:/project/config/" + projectId + "?error";
        }
        return "redirect:/project/config/" + projectId;
    }

    @RequestMapping(value = "/unassign/{id}", method = RequestMethod.POST)
    @PreAuthorize("isAuthenticated() and @permissionChecker.isProjectOwner(#projectId)")
    @ResponseBody
    public void unassignUser(@PathVariable("id") Integer projectId,
                             @RequestParam("userId") Integer userId) {
        projectService.unassignUser(projectId, userId);
    }

    @RequestMapping(value = "/permission/{id}", method = RequestMethod.POST)
    @PreAuthorize("isAuthenticated() and @permissionChecker.isProjectOwner(#projectId)")
    @ResponseBody
    public void setUserPermission(@PathVariable("id") Integer projectId,
                             @RequestParam("userId") Integer userId,
                             @RequestParam("permission") Permission permission) {
        projectService.setUserPermission(projectId, userId, permission);
    }

}

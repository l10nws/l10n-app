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
import ws.l10n.portal.domain.MessageBundle;
import ws.l10n.portal.domain.Permission;
import ws.l10n.portal.domain.PortalUser;
import ws.l10n.portal.repository.BundleRepository;
import ws.l10n.portal.repository.LocaleRepository;
import ws.l10n.portal.repository.ProjectRepository;
import ws.l10n.portal.service.BundleService;
import ws.l10n.portal.service.ProjectService;
import ws.l10n.portal.ui.security.PortalSecurity;

/**
 * @author Sergey Boguckiy
 */
@Controller
@RequestMapping("/bundle")
public class BundleController {

    @Autowired
    private BundleService bundleService;
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private ProjectService projectService;
    @Autowired
    private BundleRepository bundleRepository;
    @Autowired
    private LocaleRepository localeRepository;

    @RequestMapping(method = RequestMethod.GET)
    @PreAuthorize("isAuthenticated() and @permissionChecker.canReadProject(#projectId)")
    public String projectBundles(@RequestParam("p") Integer projectId, ModelMap map) {
        map.put("project", projectRepository.getProjectViewByProjectId(projectId));
        return "project_bundles";
    }

    @RequestMapping(value = "/create", params = "p", method = RequestMethod.GET)
    @PreAuthorize("isAuthenticated() and @permissionChecker.canWriteProject(#projectId)")
    public String createBundleView(@RequestParam(value = "p") Integer projectId, ModelMap map) {
        map.put("locales", localeRepository.getLocaleViews());
        map.put("project", projectRepository.get(projectId));
        return "create_project_bundle";
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    @PreAuthorize("isAuthenticated()")
    public String createBundleView(ModelMap map) {
        PortalUser currentUser = PortalSecurity.currentUser();

        map.put("locales", localeRepository.getLocaleViews());

        map.put("projects", projectService.listByOwnerIdAndPermissions(currentUser.getId(), Permission.WRITE));
        return "create_bundle";

    }

    @RequestMapping(value = "/create", params = "projectId", method = RequestMethod.POST)
    @PreAuthorize("isAuthenticated() and @permissionChecker.canWriteProject(#projectId)")
    public String createBundleForProjectId(@RequestParam("projectId") Integer projectId,
                                           @RequestParam("label") String label,
                                           @RequestParam("defaultLocaleId") String defaultLocaleId,
                                           @RequestParam("version") String version) {
        bundleService.create(projectId, label, version, defaultLocaleId, PortalSecurity.currentUser().getId());
        return "redirect:/bundle?p=" + projectId;
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @PreAuthorize("isAuthenticated()")
    public String createBundleByProjectIdOrName(
            @RequestParam("projectIdOrName") String projectIdOrName,
            @RequestParam("label") String label,
            @RequestParam("defaultLocaleId") String defaultLocaleId,
            @RequestParam("version") String version) {
        bundleService.create(projectIdOrName, projectIdOrName, label, version, defaultLocaleId, PortalSecurity.currentUser().getId());
        return "redirect:/bundle/all";
    }


    @RequestMapping(value = "/edit/{bundleId}", method = RequestMethod.GET)
    @PreAuthorize("isAuthenticated() and @permissionChecker.canWriteBundle(#bundleId)")
    public String editView(@PathVariable("bundleId") Integer bundleId, ModelMap map) {
        MessageBundle messageBundle = bundleRepository.get(bundleId);
        map.put("project", projectRepository.get(messageBundle.getProjectId()));
        map.put("bundle", messageBundle);
        return "create_project_bundle";
    }

    @RequestMapping(value = "/edit/{bundleId}", method = RequestMethod.POST)
    @PreAuthorize("isAuthenticated() and @permissionChecker.canWriteBundle(#bundleId)")
    public String edit(
            @RequestParam("bundleId") Integer bundleId,
            @RequestParam("projectId") Integer projectId,
            @RequestParam("label") String label) {
        bundleService.update(bundleId, label);
        return "redirect:/bundle?p=" + projectId;
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @PreAuthorize("isAuthenticated() and @permissionChecker.canWriteBundle(#bundleId)")
    @ResponseBody
    public void delete(@RequestParam("bundleId") Integer bundleId) {
        bundleService.delete(bundleId);
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public String all(ModelMap map) {
        map.put("projects", projectService.allProjectViews(PortalSecurity.currentUser().getId()));
        return "bundle_list";
    }

}

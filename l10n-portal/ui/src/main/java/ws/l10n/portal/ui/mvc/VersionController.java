package ws.l10n.portal.ui.mvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import ws.l10n.portal.domain.MessageBundle;
import ws.l10n.portal.domain.Version;
import ws.l10n.portal.repository.BundleRepository;
import ws.l10n.portal.repository.LocaleRepository;
import ws.l10n.portal.repository.ProjectRepository;
import ws.l10n.portal.repository.VersionRepository;
import ws.l10n.portal.service.VersionService;

import javax.servlet.http.HttpServletResponse;

/**
 * @author Sergey Boguckiy
 */
@Controller
@RequestMapping("/version")
public class VersionController {

    @Autowired
    private VersionRepository versionRepository;
    @Autowired
    private BundleRepository bundleRepository;
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private VersionService versionService;
    @Autowired
    private LocaleRepository localeRepository;

    @RequestMapping
    @PreAuthorize("isAuthenticated() and @permissionChecker.canReadBundle(#bundleId)")
    public String all(@RequestParam("b") Integer bundleId, ModelMap map) {

        map.put("versions", versionRepository.getByBundleId(bundleId));
        map.put("bundleId", bundleId);
        MessageBundle bundle = bundleRepository.get(bundleId);
        map.put("bundle", bundle);
        map.put("projectName", projectRepository.getProjectName(bundle.getProjectId()));
        map.put("messageBundleLabel", bundleRepository.getBundleLabel(bundleId));
        return "versions";
    }

    @RequestMapping(value = "/delete/{versionId}", method = RequestMethod.POST)
    @PreAuthorize("isAuthenticated() and @permissionChecker.canWriteVersion(#versionId)")
    @ResponseBody
    public void deleteVersion(@PathVariable("versionId") Integer versionId) {
        versionService.delete(versionId);
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public ModelAndView createView(@RequestParam Integer bundleId) {
        ModelAndView modelAndView = new ModelAndView("edit_version");
        modelAndView.addObject("versions", versionRepository.getByBundleId(bundleId));
        modelAndView.addObject("bundleId", bundleId);
        return modelAndView;
    }

    @RequestMapping(value = "/edit/{versionId}", method = RequestMethod.GET)
    @PreAuthorize("isAuthenticated() and @permissionChecker.canReadVersion(#versionId)")
    public ModelAndView editView(@PathVariable("versionId") Integer versionId) {
        ModelAndView modelAndView = new ModelAndView("edit_version");
        Version version = versionRepository.get(versionId);
        modelAndView.addObject("version", version);
        modelAndView.addObject("bundleId", version.getMessageBundleId());
        modelAndView.addObject("locales", localeRepository.getLocalesByVersionId(versionId));
        return modelAndView;
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @PreAuthorize("isAuthenticated() and @permissionChecker.canWriteBundle(#bundleId)")
    public String create(@RequestParam("bundleId") Integer bundleId,
                         @RequestParam("version") String version,
                         @RequestParam("fromVersionId") Integer fromVersionId) {

        versionService.create(bundleId, version, fromVersionId);

        return "redirect:/version?b=" + bundleId;
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @PreAuthorize("isAuthenticated() and @permissionChecker.canWriteBundle(#bundleId)")
    public String save(@RequestParam("id") Integer id,
                       @RequestParam("bundleId") Integer bundleId,
                       @RequestParam("version") String version,
                       @RequestParam("defaultLocaleId") String defaultLocaleId) {

        versionRepository.updateLabelAndDefaultLocale(id, version, defaultLocaleId);

        return "redirect:/version?b=" + bundleId;
    }


    @RequestMapping(value = "/locale", method = RequestMethod.POST)
    @PreAuthorize("isAuthenticated() and @permissionChecker.canWriteVersion(#versionId)")
    @ResponseBody
    public void setDefaultLocale(@RequestParam("versionId") Integer versionId,
                                 @RequestParam("defaultLocaleId") String defaultLocaleId) {
        versionService.setDefaultLocale(versionId, defaultLocaleId);
    }

    @RequestMapping(value = "/exist", method = RequestMethod.POST)
    @PreAuthorize("isAuthenticated() and @permissionChecker.canReadBundle(#bundleId)")
    @ResponseBody
    public void exist(@RequestParam("bundleId") Integer bundleId,
                              @RequestParam(value = "versionId", required = false) Integer versionId,
                              @RequestParam("version") String versionLabel,
                              HttpServletResponse response) {
        Version version = versionRepository.getByBundleIdAndVersionName(bundleId, versionLabel);
        if (version == null || version.getId().equals(versionId)) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
        }
    }

}

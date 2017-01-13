package ws.l10n.portal.ui.mvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import ws.l10n.portal.domain.PortalUser;
import ws.l10n.portal.domain.view.ProjectView;
import ws.l10n.portal.service.ProjectService;
import ws.l10n.portal.ui.security.PortalSecurity;

import java.util.List;

/**
 * @author Anton Mokshyn
 */
@Controller
public class DevAccessController {

    @Autowired
    private ProjectService projectService;

    @RequestMapping(value = "/dev/access")
    public String get(ModelMap map) {

        PortalUser user = PortalSecurity.currentUser();

        List<ProjectView> projects = projectService.allProjectViews(user.getId());
        map.put("projects", projects);

        return "api_access";
    }
}

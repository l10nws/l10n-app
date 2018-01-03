package ws.l10n.portal.ui.mvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ws.l10n.portal.domain.PortalUser;
import ws.l10n.portal.repository.PortalUserRepository;
import ws.l10n.portal.ui.security.PortalSecurity;

/**
 * @author Serhii Bohutskyi
 */
@Controller
public class AuthController {

    @Autowired
    public PortalUserRepository portalUserRepository;

    @RequestMapping(value = "/auth", method = RequestMethod.GET)
    public String auth(@RequestParam String authToken) {
        PortalUser user = portalUserRepository.getByAuthToken(authToken);
        if (user != null) {
            PortalSecurity.login(user);
            return "redirect:/project/all";
        } else {
            return "redirect:/login";
        }
    }
}

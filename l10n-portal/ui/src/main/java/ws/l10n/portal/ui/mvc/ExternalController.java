package ws.l10n.portal.ui.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ws.l10n.portal.ui.security.PortalSecurity;

/**
 * @author Sergey Boguckiy
 */
@Controller
public class ExternalController {

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        if (PortalSecurity.isLoggedin()) {
            return "redirect:/project/all";
        }
        return "login";
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String register() {
        if (PortalSecurity.isLoggedin()) {
            return "redirect:/project/all";
        }
        return "register";
    }

    @RequestMapping(value = "/404", method = RequestMethod.GET)
    public String error404() {
        return "error/404";
    }

    @RequestMapping(value = "/500", method = RequestMethod.GET)
    public String error500() {
        return "error/500";
    }

    @RequestMapping(value = "/401", method = RequestMethod.GET)
    public String error401() {
        return "error/401";
    }

}

package ws.l10n.portal.ui.mvc;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import ws.l10n.common.logging.LoggerUtils;
import ws.l10n.portal.domain.PortalUser;
import ws.l10n.portal.service.PortalUserService;
import ws.l10n.portal.service.exception.ActivationException;
import ws.l10n.portal.service.exception.EmailExistsException;
import ws.l10n.portal.ui.security.PortalSecurity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Sergey Boguckiy
 */
@Controller
public class RegisterController {

    @Autowired
    private PortalUserService portalUserService;

    private Logger logger = LoggerUtils.getLogger();

    @Value("${l10n.activation.enabled}")
    private Boolean emailActivationEnabled = true;

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String registerWithoutLogin(@RequestParam String email,
                                       @RequestParam String password,
                                       HttpServletRequest request) {
        try {
            if (emailActivationEnabled) {
                String appUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
                portalUserService.registerWithActivation(email.trim(), password, appUrl);
                request.setAttribute("email", email);
                return "register_success";
            } else {
                PortalUser user = portalUserService.register(email, password, true);
                PortalSecurity.login(user);
                return "redirect:/project/all";
            }
        } catch (EmailExistsException ex) {
            logger.debug(ex.getMessage(), ex);
            return "register?error";
        }
    }

    @RequestMapping(value = "/register/activate", method = RequestMethod.GET)
    public String activate(@RequestParam String email,
                           @RequestParam String token,
                           Model model) throws IOException {
        try {
            portalUserService.activate(email, token);
        } catch (ActivationException ex) {
            logger.debug(ex.getMessage(), ex);
            return "activate_failure";
        }
        return "activate_success";
    }

    @RequestMapping(value = "/register/exist", method = RequestMethod.POST)
    @ResponseBody
    public void registerExist(@RequestParam("email") String email, HttpServletResponse response) {
        if (!portalUserService.isEmailExist(email)) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
        }
    }


}

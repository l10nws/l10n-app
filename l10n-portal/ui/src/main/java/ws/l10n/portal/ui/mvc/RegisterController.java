package ws.l10n.portal.ui.mvc;

import com.google.common.base.Function;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import ws.l10n.portal.domain.PortalUser;
import ws.l10n.portal.service.PortalUserService;
import ws.l10n.portal.ui.security.PortalSecurity;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Sergey Boguckiy
 */
@Controller
public class RegisterController {

    @Autowired
    private PortalUserService portalUserService;

    @RequestMapping(name = "/registerAndLogin", method = RequestMethod.POST)
    public String register(@RequestParam String email,
                           @RequestParam String password) throws IOException {


        boolean isSuccess = portalUserService.register(email.trim(), password, new Function<PortalUser, Boolean>() {
            public Boolean apply(PortalUser user) {
                PortalSecurity.login(user);
                return true;
            }
        });

        if (isSuccess) {
            return "redirect:/project/all";
        } else {
            return "redirect:/register?error";
        }
    }

    @RequestMapping(value = "/register/exist", method = RequestMethod.POST)
    @ResponseBody
    public void registerExist(@RequestParam("email") String email, HttpServletResponse response) {
        if (!portalUserService.isEmailExist(email)) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
        }
    }


}

package ws.l10n.portal.ui.mvc;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Serhii Bohutskyi
 */
@Controller
public class RootController {

    @RequestMapping("/")
    public String landing() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()
                && !authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ANONYMOUS"))) {
            return "redirect:project/all";
        }
        return "redirect:/login";
    }

    @RequestMapping("/index")
    public String index() {
        return "redirect:/";
    }
}

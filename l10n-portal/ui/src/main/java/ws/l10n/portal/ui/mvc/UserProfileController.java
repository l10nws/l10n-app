package ws.l10n.portal.ui.mvc;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import ws.l10n.portal.domain.PortalUser;
import ws.l10n.portal.repository.PortalUserRepository;
import ws.l10n.portal.ui.security.PortalSecurity;
import ws.l10n.portal.ui.security.PortalUserDetails;

import javax.servlet.http.HttpServletResponse;

import static org.springframework.util.Assert.notNull;

/**
 * @author Anton Mokshyn
 */
@Controller
@RequestMapping("/profile")
public class UserProfileController {

    @Autowired
    private PortalUserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @RequestMapping("/edit")
    @PreAuthorize("isAuthenticated()")
    public String edit(ModelMap map) {
        map.put("user", PortalSecurity.currentUser());
        return "edit_profile";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @PreAuthorize("isAuthenticated()")
    public String save(PortalUser user, @RequestParam("newPassword") String newPassword) {
        PortalUser currentUser = PortalSecurity.currentUser();
        notNull(currentUser);
        PortalUserDetails.Builder builder = PortalUserDetails.builder().from(currentUser)
                .setEmail(user.getEmail())
                .setFirstName(user.getFirstName())
                .setLastName(user.getLastName());

        if(StringUtils.isNoneEmpty(newPassword)) {
            builder.setPassword(passwordEncoder.encode(newPassword));
        }
        PortalUserDetails userToUpdate = builder.build();
        userRepository.update(userToUpdate);
        PortalSecurity.login(userToUpdate);
        return "redirect:/profile/edit?success";
    }

    @RequestMapping(value = "/register/exist", method = RequestMethod.POST)
    @ResponseBody
    public void registerExist(@RequestParam("email") String email, HttpServletResponse response) {
        if(!isEmailExist(email)) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
        }
    }

    @RequestMapping(value = "/edit/exist", method = RequestMethod.POST)
    @ResponseBody
    public void editExist(@RequestParam("email") String email, HttpServletResponse response) {
        PortalUser currentUser = PortalSecurity.currentUser();
        notNull(currentUser);
        if(currentUser.getEmail().equals(email) || !isEmailExist(email)) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
        }
    }

    private boolean isEmailExist(String email) {
        return userRepository.getByEmail(email) != null;
    }
}

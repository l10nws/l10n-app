package ws.l10n.portal.ui.mvc;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import ws.l10n.common.util.TokenGenerator;
import ws.l10n.portal.domain.PortalUser;
import ws.l10n.portal.domain.Role;
import ws.l10n.portal.repository.PortalUserRepository;
import ws.l10n.portal.service.PortalUserService;
import ws.l10n.portal.ui.security.PortalSecurity;
import ws.l10n.portal.ui.security.PortalUserDetails;

import javax.servlet.http.HttpServletResponse;

import static org.springframework.util.Assert.notNull;

/**
 * @author Anton Mokshyn
 */
@Controller
@RequestMapping("/user")
@PreAuthorize("hasRole('SUPERUSER') || hasRole('ADMIN')")
public class UserController {

    @Autowired
    private PortalUserRepository portalUserRepository;

    @Autowired
    private PortalUserService portalUserService;

    @Autowired
    private TokenGenerator tokenGenerator;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public String all(ModelMap map) {
        map.put("users", portalUserRepository.all());
        return "users";
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create(ModelMap map) {

        return "create_user";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String doCreate(PortalUser user, @RequestParam("password") String password) {
        PortalUser portalUser = PortalUser.builder().from(user)
                .setAccessToken(tokenGenerator.generate())
                .setPassword(passwordEncoder.encode(password))
                .setRole(user.getRole())
                .build();
        portalUserRepository.save(portalUser);
        return "redirect:/user/all";
    }


    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String edit(@PathVariable("id") Integer userId, ModelMap map) {
        map.put("user", portalUserRepository.get(userId));
        return "create_user";
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
    public String doEdit(PortalUser user, @PathVariable("id") Integer userId, @RequestParam("password") String password) throws IllegalAccessException {
        PortalUser source = portalUserRepository.get(userId);
        if(Role.ROLE_SUPERUSER == source.getRole()) {
            PortalUserDetails currentUser = PortalSecurity.currentUser();
            if (Role.ROLE_SUPERUSER != currentUser.getRole()) {
                throw new IllegalAccessException();
            }
        }
        PortalUser.Builder builder = PortalUser.builder().from(source)
                .setEmail(user.getEmail())
                .setFirstName(user.getFirstName())
                .setLastName(user.getLastName());
        if (user.getRole() != null && Role.ROLE_SUPERUSER != source.getRole()) {
            builder.setRole(user.getRole());
        }
        if (StringUtils.isNoneEmpty(password)) {
            builder.setPassword(passwordEncoder.encode(password));
        }
        PortalUser portalUser = builder.build();
        portalUserRepository.update(portalUser);
        return "redirect:/user/all";
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public void delete(@RequestParam("userId") Integer userId) {
        portalUserService.delete(userId);
    }

    @RequestMapping(value = "/create/exist", method = RequestMethod.POST)
    @ResponseBody
    public void createExist(@RequestParam("email") String email, HttpServletResponse response) {
        if (!isEmailExist(email)) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
        }
    }

    @RequestMapping(value = "/edit/exist", method = RequestMethod.POST)
    @ResponseBody
    public void editExist(@RequestParam("email") String email, @RequestParam("userId") Integer userId, HttpServletResponse response) {
        PortalUser user = portalUserRepository.get(userId);
        notNull(user);
        if (user.getEmail().equals(email) || !isEmailExist(email)) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
        }
    }

    private boolean isEmailExist(String email) {
        return portalUserRepository.getByEmail(email) != null;
    }


}

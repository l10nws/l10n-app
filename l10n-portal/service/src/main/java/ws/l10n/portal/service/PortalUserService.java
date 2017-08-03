package ws.l10n.portal.service;

import com.google.common.base.Function;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ws.l10n.common.logging.LoggerUtils;
import ws.l10n.common.util.TokenGenerator;
import ws.l10n.portal.config.meta.UserMeta;
import ws.l10n.portal.domain.PortalUser;
import ws.l10n.portal.domain.Project;
import ws.l10n.portal.domain.Role;
import ws.l10n.portal.repository.PortalUserRepository;
import ws.l10n.portal.repository.ProjectRepository;
import ws.l10n.portal.service.event.PortalUserChangedEvent;

import java.util.Arrays;
import java.util.List;

/**
 * @author Anton Mokshyn
 */
@Service
@Transactional
public class PortalUserService {

    private Logger logger = LoggerUtils.getLogger();

    @Autowired
    private PortalUserRepository userRepository;
    @Autowired
    private ProjectService projectService;
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private TokenGenerator tokenGenerator;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public PortalUser createUser(String email, String rawPassword) {
        return create(email, rawPassword, null, null, Role.ROLE_USER);
    }

    public PortalUser createAdmin(String email, String rawPassword) {
        return create(email, rawPassword, null, null, Role.ROLE_ADMIN);
    }

    public PortalUser createSuperuser(String email, String rawPassword) {
        return create(email, rawPassword, null, null, Role.ROLE_SUPERUSER);
    }

    public PortalUser get(Integer id) {
        return userRepository.get(id);
    }

    public PortalUser create(String email, String rawPassword, String firstName, String lastName, Role role) {
        logger.info("Create user, email [{}], role [{}]", email, role);

        PortalUser portalUser = PortalUser.builder()
                .setEmail(email)
                .setFirstName(firstName)
                .setLastName(lastName)
                .setPassword(passwordEncoder.encode(rawPassword))
                .setAccessToken(tokenGenerator.generate())
                .setRole(role)
                .build();
        userRepository.save(portalUser);
        return portalUser;
    }

    public PortalUser update(PortalUser portalUser) {
        userRepository.update(portalUser);
        return portalUser;
    }

    public String changeAuthToken(Integer portalUserId) {
        logger.debug("Change auth token for user ID [{}]", portalUserId);
        PortalUser portalUser = userRepository.get(portalUserId);
        return changeAuthToken(portalUser);
    }


    public String changeAuthToken(PortalUser portalUser) {
        logger.debug("Change auth token for user [{}]", portalUser.getEmail());

        String authToken = tokenGenerator.generate();
        userRepository.saveAuthToken(portalUser.getId(), authToken);
        return authToken;
    }

    public void changePassword(int userId, String rawPassword) {
        logger.debug("Change password user with id [{}]", userId);
        userRepository.updatePassword(userId, passwordEncoder.encode(rawPassword));
    }

    public void delete(Integer userId) {
        logger.info("Delete user with id [{}]", userId);

        List<Project> projectsByOwnerId = projectRepository.getProjectsByOwnerId(userId);
        for (Project project : projectsByOwnerId) {
            projectService.delete(project.getId());
        }
        userRepository.delete(userId);
    }

    public boolean register(String email, String password, Function<PortalUser, Boolean> onSuccess) {
        if (isEmailExist(email)) {
            return false;
        } else {
            PortalUser portalUser = createUser(email, password);
            return onSuccess.apply(portalUser);
        }
    }

    public boolean isEmailExist(String email) {
        return userRepository.getByEmail(email) != null;
    }


    @EventListener
    public void superusersChanged(PortalUserChangedEvent event) {
        logger.debug("PortalUserChangedEvent received!");

        List<UserMeta> list = event.getUserMeta();
        logger.debug("Superusers list [{}]!", Arrays.toString(list.toArray()));

        for (UserMeta userMeta : list) {
            String email = userMeta.getEmail();
            String password = userMeta.getPassword();

            PortalUser user = userRepository.getByEmail(email);
            if (user != null) {
                logger.info("Superuser [{}] exists, updating password", email);
                user.setPassword(passwordEncoder.encode(password));
                user.setRole(Role.ROLE_SUPERUSER);
                userRepository.update(user);
            } else {
                logger.debug("Creating superuser [{}]", email);
                createSuperuser(email, password);
            }
        }
    }

}


package ws.l10n.portal.ui.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import ws.l10n.portal.domain.PortalUser;

/**
 *
 */
public class PortalSecurity {

    public static PortalUserDetails currentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof PortalUserDetails) {
                return (PortalUserDetails) principal;
            }
        }
        throw new RuntimeException("User not logged in!");
    }

    public static void login(PortalUser user) {
        PortalUserDetails portalUserDetails = PortalUserDetails.builder().from(user).build();
        login((UserDetails) portalUserDetails);
    }

    public static void login(PortalUserDetails portalUserDetails) {
        login((UserDetails) portalUserDetails);
    }

    public static void login(UserDetails user) {
        Authentication auth = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

}

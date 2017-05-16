package ws.l10n.api.web;

import ws.l10n.portal.domain.PortalUser;

import javax.servlet.http.HttpSession;

/**
 * @author Sergey Boguckiy
 */
public class SecurityUtils {

    private static final String USER_ATTRIBUTE = "L10N_CURRENT_USER";

    public static void authorize(PortalUser user, HttpSession session) {
        session.setAttribute(USER_ATTRIBUTE, user);
    }

    public static boolean isAuthorized(HttpSession session) {
        return getUser(session) != null;
    }

    public static PortalUser getUser(HttpSession session) {
        return (PortalUser) session.getAttribute(USER_ATTRIBUTE);
    }

}

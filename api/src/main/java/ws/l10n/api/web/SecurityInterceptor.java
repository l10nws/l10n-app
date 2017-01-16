package ws.l10n.api.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import ws.l10n.api.ApiResponseStatus;
import ws.l10n.api.ErrorResponse;
import ws.l10n.portal.domain.PortalUser;
import ws.l10n.portal.repository.PortalUserRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static ws.l10n.api.web.SecurityUtils.authorize;
import static ws.l10n.api.web.SecurityUtils.isAuthorized;

public class SecurityInterceptor implements HandlerInterceptor {

    public static final String SECURITY_TOKEN_HEADER = "Access-Token";
    public static final String SECURITY_TOKEN_LOWER_HEADER = SECURITY_TOKEN_HEADER.toLowerCase();
    public static final String SECURITY_TOKEN_UPPER_HEADER = SECURITY_TOKEN_HEADER.toUpperCase();
    public static final String SECURITY_TOKEN_PARAMETER = "accessToken";
    public static final String SECURITY_TOKEN_SHORT_PARAMETER = "at";

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Autowired
    private PortalUserRepository userRepository;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();

        if (isAuthorized(session)) {
            return true;
        }

        String token = getToken(request);
        if (StringUtils.isNotEmpty(token)) {
            boolean success = authorizeUser(token, session);
            if (success) {
                return true;
            } else {
                response.setStatus(HttpStatus.FORBIDDEN.value());
                OBJECT_MAPPER.writeValue(response.getWriter(), new ErrorResponse(ApiResponseStatus.ACCESS_DENIED,
                        "Bad or expired token."));
            }
        } else {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            OBJECT_MAPPER.writeValue(response.getWriter(), new ErrorResponse(ApiResponseStatus.UNAUTHORIZED,
                    "Access token is not specified."));
        }
        return false;
    }

    private void handleException(Exception e, HttpServletResponse response) throws IOException {
        //todo here switch all exception cases
        //and default
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        OBJECT_MAPPER.writeValue(response.getWriter(), new ErrorResponse(ApiResponseStatus.INTERNAL_SERVER_ERROR,
                "An error occurred on the L10n servers"));
    }

    private String getToken(HttpServletRequest request) {
        String headerLowerToken = request.getHeader(SECURITY_TOKEN_LOWER_HEADER);
        if (StringUtils.isNotEmpty(headerLowerToken)) {
            return headerLowerToken;
        }
        String headerUpperToken = request.getHeader(SECURITY_TOKEN_UPPER_HEADER);
        if (StringUtils.isNotEmpty(headerUpperToken)) {
            return headerLowerToken;
        }

        String headerToken = request.getHeader(SECURITY_TOKEN_HEADER);
        if (StringUtils.isNotEmpty(headerToken)) {
            return headerToken;
        }
        String parameterToken = request.getParameter(SECURITY_TOKEN_PARAMETER);
        if (StringUtils.isNotEmpty(parameterToken)) {
            return parameterToken;
        }
        String parameterTokenShort = request.getParameter(SECURITY_TOKEN_SHORT_PARAMETER);
        if (StringUtils.isNotEmpty(parameterTokenShort)) {
            return parameterTokenShort;
        }
        return null;
    }

    private boolean authorizeUser(String token, HttpSession session) {
        PortalUser user = userRepository.getByAccessToken(token);
        authorize(user, session);
        return user != null;
    }


    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
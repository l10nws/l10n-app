package ws.l10n.portal.repository;

import org.apache.ibatis.annotations.Param;
import ws.l10n.portal.domain.PortalUser;

import java.util.List;

/**
 * @author Sergey Boguckiy
 */
public interface PortalUserRepository {
    void save(PortalUser portalUser);

    void update(PortalUser portalUser);

    List<PortalUser> all();

    void updateAccessToken(Integer id, String accessToken);

    PortalUser get(Integer id);

    PortalUser getByEmail(String email);

    List<PortalUser> getByProjectId(Integer projectId);

    PortalUser getByAccessToken(String token);

    String getAuthToken(@Param("userId") int userId);

    String getAuthTokenByEmail(@Param("email") String email);

    PortalUser getByAuthToken(@Param("authToken") String authToken);

    void saveAuthToken(@Param("userId") Integer userId, @Param("authToken") String authToken);

    void updatePassword(@Param("userId") int userId, @Param("password") String password);

    void delete(Integer userId);
}

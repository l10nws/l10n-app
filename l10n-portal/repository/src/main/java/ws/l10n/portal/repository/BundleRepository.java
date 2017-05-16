package ws.l10n.portal.repository;

import org.apache.ibatis.annotations.Param;
import ws.l10n.portal.domain.MessageBundle;

import java.util.List;

/**
 * @author Sergey Boguckiy
 */
public interface BundleRepository {

    MessageBundle getByUid(String uuid);

    List<MessageBundle> getByProjectId(Integer projectId);

    MessageBundle get(Integer id);

    void insert(MessageBundle bundle);

    void updateUid(Integer id, String uid);

    void update(MessageBundle bundle);

    String getBundleLabel(Integer bundleId);

    String getProjectName(Integer bundleId);

    Integer getProjectId(@Param("bundleId") Integer bundleId);

    void delete(Integer bundleId);
}

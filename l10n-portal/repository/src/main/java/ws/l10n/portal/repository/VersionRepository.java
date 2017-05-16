package ws.l10n.portal.repository;

import ws.l10n.portal.domain.Version;

import java.util.Date;
import java.util.List;

/**
 * @author Sergey Boguckiy
 */
public interface VersionRepository {

    void insert(Version version);

    List<Version> getByBundleId(Integer bundleId);

    Version getByBundleIdAndVersionName(Integer bundleId, String version);

    Version get(Integer id);

    void update(Version version);

    void updateLabelAndDefaultLocale(Integer id, String label, String defaultLocaleId);

    void updateModificationDate(Integer id, Date modificationDate);

    void delete(Integer versionId);

    Version getByBundleUidAndVersionName(String bundleUid, String versionName);

    void updateViewDate(Integer id);

    Integer getProjectId(Integer versionId);
}

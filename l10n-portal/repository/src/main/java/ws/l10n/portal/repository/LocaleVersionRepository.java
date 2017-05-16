package ws.l10n.portal.repository;

import ws.l10n.portal.domain.LocaleVersion;

import java.util.List;

/**
 * @author Sergey Boguckiy
 */
public interface LocaleVersionRepository {

    void insert(LocaleVersion localeVersion);

    List<LocaleVersion> getByVersionId(Integer versionId);

    List<String> getLocaleIdsByVersionId(Integer versionId);

    void deleteByVersionIdAndLocaleId(Integer versionId, String localeId);

}

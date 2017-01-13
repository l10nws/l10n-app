package ws.l10n.portal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ws.l10n.portal.domain.LocaleVersion;
import ws.l10n.portal.domain.Version;
import ws.l10n.portal.repository.BundleRepository;
import ws.l10n.portal.repository.LocaleRepository;
import ws.l10n.portal.repository.LocaleVersionRepository;
import ws.l10n.portal.repository.MessageRepository;
import ws.l10n.portal.repository.VersionRepository;

import java.util.Date;
import java.util.List;

/**
 * @author Sergey Boguckiy
 */
@Service
@Transactional
public class VersionService {
    @Autowired
    private LocaleRepository localeRepository;
    @Autowired
    private BundleRepository bundleRepository;
    @Autowired
    private VersionRepository versionRepository;
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private LocaleVersionRepository localeVersionRepository;

    public void create(Integer bundleId, String versionLabel, Integer fromVersionId) {

        Version fromVersion = versionRepository.get(fromVersionId);
        Version version = new Version(null, bundleId, versionLabel, fromVersion.getDefaultLocaleId(), new Date(), null);

        versionRepository.insert(version);

        copyLocales(version.getId(), fromVersionId);

        messageRepository.createVersion(version.getId(), fromVersionId);
    }

    private void copyLocales(Integer versionId, Integer fromVersionId) {
        List<LocaleVersion> localeVersions = localeVersionRepository.getByVersionId(fromVersionId);
        for (LocaleVersion localeVersion : localeVersions) {
            localeVersion.setId(null);
            localeVersion.setVersionId(versionId);
            localeVersionRepository.insert(localeVersion);
        }
    }

    public void setDefaultLocale(Integer versionId, String defaultLocaleId) {
        Version version = versionRepository.get(versionId);
        version.setDefaultLocaleId(defaultLocaleId);
        versionRepository.update(version);
    }

    public void deleteLocale(Integer versionId, String localeId) {
        localeVersionRepository.deleteByVersionIdAndLocaleId(versionId, localeId);
        messageRepository.deleteByVersionIdAndLocaleId(versionId, localeId);
    }

    public void delete(Integer versionId) {
        versionRepository.delete(versionId);
        messageRepository.deleteByVersionId(versionId);
    }
}

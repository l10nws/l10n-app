package ws.l10n.portal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ws.l10n.portal.domain.LocaleVersion;
import ws.l10n.portal.domain.Version;
import ws.l10n.portal.repository.LocaleVersionRepository;
import ws.l10n.portal.repository.MessageRepository;
import ws.l10n.portal.repository.VersionRepository;

/**
 * @author Sergey Boguckiy
 */
@Service
public class LocaleService {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private LocaleVersionRepository localeVersionRepository;

    @Autowired
    private VersionRepository versionRepository;

    public void addLocale(Integer versionId, String localeId, boolean shouldCopy) {
        LocaleVersion localeVersion = new LocaleVersion();
        localeVersion.setLocaleId(localeId);
        localeVersion.setVersionId(versionId);
        localeVersionRepository.insert(localeVersion);
        Version version = versionRepository.get(versionId);
        if (shouldCopy) {
            String defaultLocaleId = version.getDefaultLocaleId();
            messageRepository.copyMessages(defaultLocaleId, localeId, versionId);
        }
    }
}

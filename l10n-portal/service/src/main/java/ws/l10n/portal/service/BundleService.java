package ws.l10n.portal.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ws.l10n.common.util.TokenGenerator;
import ws.l10n.portal.domain.LocaleVersion;
import ws.l10n.portal.domain.MessageBundle;
import ws.l10n.portal.domain.Project;
import ws.l10n.portal.domain.ProjectBase;
import ws.l10n.portal.domain.Version;
import ws.l10n.portal.repository.BundleRepository;
import ws.l10n.portal.repository.LocaleVersionRepository;
import ws.l10n.portal.repository.MessageRepository;
import ws.l10n.portal.repository.ProjectRepository;
import ws.l10n.portal.repository.VersionRepository;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author Sergey Boguckiy
 */
@Service
@Transactional
public class BundleService {

    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private LocaleVersionRepository localeVersionRepository;
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private BundleRepository bundleRepository;
    @Autowired
    private TokenGenerator tokenGenerator;
    @Autowired
    private VersionRepository bundleVersionRepository;

    public MessageBundle save(MessageBundle bundle) {
        if (bundle.getId() == null) {
            bundleRepository.insert(bundle);
        } else {
            bundleRepository.update(bundle);
        }
        return bundle;
    }

    public void update(Integer bundleId, String label) {
        MessageBundle messageBundle = bundleRepository.get(bundleId);
        messageBundle.setLabel(label);
        bundleRepository.update(messageBundle);
    }

    public MessageBundle create(String projectId, String projectName, String label, String versionLabel, String defaultLocaleId, Integer ownerId) {
        ProjectBase project = null;
        if(StringUtils.isNumeric(projectId)) {
            project = projectRepository.getProjectById(Integer.parseInt(projectId));
        }
        if (project == null) {
            project = new Project();
            project.setName(projectName);
            project.setOwnerId(ownerId);
            project.setCreationDate(new Date());
            projectRepository.save(project);
        }
        return create(project.getId(), label, versionLabel, defaultLocaleId, ownerId);
    }

    public MessageBundle create(Integer projectId, String label, String versionLabel, String defaultLocaleId, Integer ownerId) {
        MessageBundle messageBundle = new MessageBundle();
        messageBundle.setProjectId(projectId);
        messageBundle.setLabel(label);
        messageBundle.setOwnerId(ownerId);
        messageBundle.setCreationDate(new Date());
        messageBundle.setUid(UUID.randomUUID().toString());
        bundleRepository.insert(messageBundle);
        bundleRepository.updateUid(messageBundle.getId(), tokenGenerator.generate(messageBundle.getId()));

        Version version = new Version();
        version.setMessageBundleId(messageBundle.getId());
        version.setVersion(versionLabel);
        version.setCreationDate(new Date());
        version.setDefaultLocaleId(defaultLocaleId);

        bundleVersionRepository.insert(version);

        LocaleVersion localeVersion = new LocaleVersion();
        localeVersion.setVersionId(version.getId());
        localeVersion.setLocaleId(defaultLocaleId);
        localeVersionRepository.insert(localeVersion);

        return messageBundle;
    }

    public void delete(Integer bundleId) {
        List<Version> versions = bundleVersionRepository.getByBundleId(bundleId);
        for (Version version : versions) {
            messageRepository.deleteByVersionId(version.getId());
        }
        bundleRepository.delete(bundleId);
    }
}

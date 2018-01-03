package ws.l10n.portal.config.web.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ContextLoaderListener;
import ws.l10n.portal.config.PortalConfigLoader;
import ws.l10n.portal.config.PortalConfigReader;
import ws.l10n.portal.config.PortalConfigValidator;
import ws.l10n.portal.config.meta.ConfigMeta;

import javax.servlet.ServletContextEvent;

/**
 *
 */
public class ConfigContextLoaderListener extends ContextLoaderListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigContextLoaderListener.class);

    public static final String PORTAL_CONFIG = "PORTAL_CONFIG";

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            ConfigMeta configMeta = new PortalConfigLoader(new PortalConfigReader(), new PortalConfigValidator()).load();

            System.setProperty("l10n.portal.database.driver", configMeta.getDatasource().getDriverClassName());
            System.setProperty("l10n.portal.database.url", configMeta.getDatasource().getUrl());
            System.setProperty("l10n.portal.database.username", configMeta.getDatasource().getUsername());
            System.setProperty("l10n.portal.database.password", configMeta.getDatasource().getPassword());

            if (configMeta.getSmtp() != null) {
                System.setProperty("l10n.mail.smtp.enable", String.valueOf(configMeta.getSmtp().isEnable()));
                System.setProperty("l10n.mail.smtp.host", configMeta.getSmtp().getHost());
                System.setProperty("l10n.mail.smtp.port", String.valueOf(configMeta.getSmtp().getPort()));
                System.setProperty("l10n.mail.smtp.username", configMeta.getSmtp().getUsername());
                System.setProperty("l10n.mail.smtp.password", configMeta.getSmtp().getPassword());
                System.setProperty("l10n.activation.enabled", String.valueOf(configMeta.getSmtp().isEmailAccountActivationEnabled()));
                System.setProperty("l10n.mail.noreply", configMeta.getMaillist().get("noreply"));
            }

            sce.getServletContext().setAttribute(PORTAL_CONFIG, configMeta);

            super.contextInitialized(sce);

        } catch (RuntimeException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        super.contextDestroyed(sce);
    }
}

package ws.l10n.portal.service;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ws.l10n.common.logging.LoggerUtils;
import ws.l10n.portal.config.meta.ConfigMeta;

import javax.servlet.ServletContext;

/**
 * @author Serhii Bohutskyi
 */
@Service
public class PortalConfigService {
    private Logger logger = LoggerUtils.getLogger();

    @Autowired
    private ServletContext servletContext;
    private volatile ConfigMeta configMeta;

    public ConfigMeta getConfigMeta() {
        if (configMeta == null) {
            synchronized (this) {
                if (configMeta == null) {
                    configMeta = (ConfigMeta) servletContext.getAttribute("PORTAL_CONFIG");
                    logger.debug("Portal configuration initialized!");
                }
            }
        }

        return configMeta;
    }
}

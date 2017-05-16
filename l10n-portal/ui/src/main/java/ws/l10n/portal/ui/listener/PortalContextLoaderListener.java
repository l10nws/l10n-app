package ws.l10n.portal.ui.listener;

import ws.l10n.portal.config.meta.ConfigMeta;
import ws.l10n.portal.config.web.listener.ConfigContextLoaderListener;
import ws.l10n.portal.service.event.PortalUserChangedEvent;

import javax.servlet.ServletContextEvent;

/**
 *
 */
public class PortalContextLoaderListener extends ConfigContextLoaderListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        super.contextInitialized(sce);

        getCurrentWebApplicationContext().publishEvent(new PortalUserChangedEvent(
                ((ConfigMeta) sce.getServletContext().getAttribute(PORTAL_CONFIG)).getSuperusers()));

    }

}

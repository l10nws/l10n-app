package ws.l10n.portal.config;

import org.springframework.util.Assert;
import ws.l10n.portal.config.meta.ConfigMeta;
import ws.l10n.portal.config.meta.UserMeta;

/**
 * @author Serhii Bohutskyi
 */
public class PortalConfigValidator {

    public void validate(final ConfigMeta configMeta) {
        Assert.notNull(configMeta, "Portal configuration cannot be empty!");

        Assert.notNull(configMeta.getDatasource(), "Datasource(<datasource>) not found in portal configuration!");
        Assert.notNull(configMeta.getDatasource().getDriverClassName(), "driverClassName not found in datasource portal configuration!");
        Assert.notNull(configMeta.getDatasource().getUrl(), "<url> not found in datasource portal configuration!");
        Assert.notNull(configMeta.getDatasource().getUsername(), "<username> not found in datasource portal configuration!");
        Assert.notNull(configMeta.getDatasource().getPassword(), "<password> not found in datasource portal configuration!");

        if (configMeta.getSmtp() != null && configMeta.getSmtp().isEnable()) {
            Assert.notNull(configMeta.getSmtp().getHost(), "<host> not found in smtp portal configuration!");
            Assert.notNull(configMeta.getSmtp().getPort(), "<port> not found in smtp portal configuration!");
            Assert.notNull(configMeta.getSmtp().getUsername(), "<username> not found in smtp portal configuration!");
            Assert.notNull(configMeta.getSmtp().getPassword(), "<password> not found in smtp portal configuration!");
            Assert.notNull(configMeta.getMaillist().get("noreply"), "'noreply' item not found in <mail-list> portal configuration!");
        }

        if (configMeta.getSuperusers() != null) {
            for (UserMeta userMeta : configMeta.getSuperusers()) {
                Assert.notNull(userMeta.getEmail(), "<email> not found in superusers portal configuration!");
                Assert.notNull(userMeta.getPassword(), "<password> not found in superusers portal configuration!");
            }
        }
    }
}

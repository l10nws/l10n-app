package ws.l10n.portal.config.meta;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author Serhii Bohutskyi
 */
public class ConfigMeta implements Serializable {

    private List<UserMeta> superusers;
    private Map<String, String> maillist;
    private SmtpMeta smtp;
    private DatasourceMeta datasource;

    public List<UserMeta> getSuperusers() {
        return superusers;
    }

    public void setSuperusers(List<UserMeta> superusers) {
        this.superusers = superusers;
    }

    public SmtpMeta getSmtp() {
        return smtp;
    }

    public void setSmtp(SmtpMeta smtp) {
        this.smtp = smtp;
    }

    public Map<String, String> getMaillist() {
        return maillist;
    }

    public void setMaillist(Map<String, String> maillist) {
        this.maillist = maillist;
    }

    public DatasourceMeta getDatasource() {
        return datasource;
    }

    public void setDatasource(DatasourceMeta datasource) {
        this.datasource = datasource;
    }

    @Override
    public String toString() {
        return "ConfigMeta{" +
                "superusers=" + String.valueOf(superusers) +
                ", smtp=" + String.valueOf(smtp) +
                '}';
    }
}

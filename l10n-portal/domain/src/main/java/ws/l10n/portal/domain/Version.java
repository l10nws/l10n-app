package ws.l10n.portal.domain;

import java.util.Date;

/**
 * @author Sergey Boguckiy
 */
public class Version {

    private Integer id;
    private Integer messageBundleId;
    private String version;
    private String defaultLocaleId;
    private Date creationDate;
    private Date modificationDate;
    private Date viewDate;

    public Version() {
    }

    public Version(Integer id, Integer messageBundleId, String version, String defaultLocaleId,
                   Date creationDate, Date modificationDate) {
        this.id = id;
        this.messageBundleId = messageBundleId;
        this.version = version;
        this.defaultLocaleId = defaultLocaleId;
        this.creationDate = creationDate;
        this.modificationDate = modificationDate;
    }

    public Date getViewDate() {
        return viewDate;
    }

    public void setViewDate(Date viewDate) {
        this.viewDate = viewDate;
    }

    public String getDefaultLocaleId() {
        return defaultLocaleId;
    }

    public void setDefaultLocaleId(String defaultLocaleId) {
        this.defaultLocaleId = defaultLocaleId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMessageBundleId() {
        return messageBundleId;
    }

    public void setMessageBundleId(Integer messageBundleId) {
        this.messageBundleId = messageBundleId;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getModificationDate() {
        return modificationDate;
    }

    public void setModificationDate(Date modificationDate) {
        this.modificationDate = modificationDate;
    }
}

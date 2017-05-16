package ws.l10n.portal.domain;

/**
 * @author Sergey Boguckiy
 */
public class LocaleVersion {
    private Integer id;
    private String localeId;
    private Integer versionId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLocaleId() {
        return localeId;
    }

    public void setLocaleId(String localeId) {
        this.localeId = localeId;
    }

    public Integer getVersionId() {
        return versionId;
    }

    public void setVersionId(Integer versionId) {
        this.versionId = versionId;
    }
}

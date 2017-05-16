package ws.l10n.portal.domain.view;

import ws.l10n.portal.domain.Version;

import java.util.Date;
import java.util.List;

/**
 * @author Sergey Boguckiy
 */
public class MessageBundleView {

    private Integer id;
    private String label;
    private String defaultLocaleId;
    private Integer projectId;
    private String uid;
    private Integer ownerId;
    private String ownerEmail;
    private Date creationDate;
    private List<Version> versions;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getDefaultLocaleId() {
        return defaultLocaleId;
    }

    public void setDefaultLocaleId(String defaultLocaleId) {
        this.defaultLocaleId = defaultLocaleId;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Integer getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }

    public String getOwnerEmail() {
        return ownerEmail;
    }

    public void setOwnerEmail(String ownerEmail) {
        this.ownerEmail = ownerEmail;
    }

    public List<Version> getVersions() {
        return versions;
    }

    public void setVersions(List<Version> versions) {
        this.versions = versions;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
}

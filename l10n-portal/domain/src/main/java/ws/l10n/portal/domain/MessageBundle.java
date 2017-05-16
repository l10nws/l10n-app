package ws.l10n.portal.domain;

import java.util.Date;

/**
 * @author Sergey Boguckiy
 */
public class MessageBundle {

    private Integer id;
    private String label;
    private Integer projectId;
    private String uid;
    private Integer ownerId;
    private Date creationDate;

    public MessageBundle() {
    }

    public MessageBundle(String label, Integer projectId, String uid, Integer ownerId, Date creationDate) {
        this.label = label;
        this.projectId = projectId;
        this.uid = uid;
        this.ownerId = ownerId;
        this.creationDate = creationDate;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

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
}

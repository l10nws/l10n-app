package ws.l10n.portal.domain;

/**
 * @author Sergey Boguckiy
 */
public class Project extends ProjectBase {

    private Integer userCount;
    private Integer bundleCount;
    private String ownerEmail;

    public Project() {
    }

    public String getOwnerEmail() {
        return ownerEmail;
    }

    public void setOwnerEmail(String ownerEmail) {
        this.ownerEmail = ownerEmail;
    }

    public Integer getUserCount() {
        return userCount;
    }

    public void setUserCount(Integer userCount) {
        this.userCount = userCount;
    }

    public Integer getBundleCount() {
        return bundleCount;
    }

    public void setBundleCount(Integer bundleCount) {
        this.bundleCount = bundleCount;
    }
}

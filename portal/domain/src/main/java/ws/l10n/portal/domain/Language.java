package ws.l10n.portal.domain;

/**
 * @author Anton Mokshyn
 */
public class Language {

    private Integer id;
    private Integer messageBundleId;
    private String name;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

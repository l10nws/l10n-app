package ws.l10n.portal.domain.view;

import ws.l10n.portal.domain.ProjectBase;

import java.util.List;

/**
 * @author Sergey Boguckiy
 */
public class ProjectView extends ProjectBase {

    private String ownerEmail;
    private List<MessageBundleView> messageBundles;

    public String getOwnerEmail() {
        return ownerEmail;
    }

    public void setOwnerEmail(String ownerEmail) {
        this.ownerEmail = ownerEmail;
    }

    public List<MessageBundleView> getMessageBundles() {
        return messageBundles;
    }

    public void setMessageBundles(List<MessageBundleView> messageBundles) {
        this.messageBundles = messageBundles;
    }
}

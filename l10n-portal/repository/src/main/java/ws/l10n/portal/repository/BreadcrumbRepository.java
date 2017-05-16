package ws.l10n.portal.repository;

import java.util.Map;

/**
 * @author Sergey Boguckiy
 */
public interface BreadcrumbRepository {

    Map<String,String> getMessagesBreadcrumb(Integer versionId);

}

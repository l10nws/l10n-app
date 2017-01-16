package ws.l10n.portal.repository;

import ws.l10n.portal.domain.view.LocaleView;

import java.util.List;

/**
 * @author Sergey Boguckiy
 */
public interface LocaleRepository {

    List<LocaleView> getLocaleViews();

    List<LocaleView> getLocalesByVersionId(Integer versionId);

    String getIdByAnyCaseId(String localeId);

    LocaleView getLocaleViewById(String id);
}

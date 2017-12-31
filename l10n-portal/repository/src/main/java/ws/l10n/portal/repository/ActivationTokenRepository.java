package ws.l10n.portal.repository;

import ws.l10n.portal.domain.ActivationToken;

public interface ActivationTokenRepository {
    ActivationToken findByUserId(Integer userId);

    void save(ActivationToken activationToken);

    void update(ActivationToken activationToken);
    
    void deleteByUserId(Integer userId);
}

package ws.l10n.portal.ui.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ws.l10n.portal.domain.PortalUser;
import ws.l10n.portal.repository.PortalUserRepository;

public class PortalUserDetailsService implements UserDetailsService {

    @Autowired
    private PortalUserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        PortalUser user = userRepository.getByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User not found! username [" + email + "]");
        }
        return PortalUserDetails.builder().from(user).build();
    }
}
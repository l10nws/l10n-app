package ws.l10n.portal.ui.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ws.l10n.portal.domain.PortalUser;
import ws.l10n.portal.domain.Role;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Sergey Boguckiy
 * @author Anton Mokshyn
 */
public class PortalUserDetails extends PortalUser implements UserDetails {

    private final Set<GrantedAuthority> authorities;
    private final Boolean accountNonExpired;
    private final Boolean accountNonLocked;
    private final Boolean credentialsNonExpired;
    private final Boolean googleSignIn;

    public PortalUserDetails(Integer id, String email, String firstName, String lastName, String password, String accessToken, Role role,
                             Boolean activated, Boolean accountNonExpired, Boolean accountNonLocked,
                             Boolean credentialsNonExpired, Set<GrantedAuthority> authorities, Boolean googleSignIn) {
        super(id, email, firstName, lastName, password, accessToken, role, activated);
        this.accountNonExpired = accountNonExpired;
        this.accountNonLocked = accountNonLocked;
        this.credentialsNonExpired = credentialsNonExpired;
        this.authorities = new HashSet<GrantedAuthority>(authorities);
        this.authorities.add(new SimpleGrantedAuthority(role.name()));
        this.googleSignIn = googleSignIn;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getUsername() {
        return getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return isActivated() ;
    }

    public boolean getGoogleSignIn() {
        return googleSignIn;
    }

    public PortalUserDetails() {
        super(null, null, null, null, null, null, null,false);
        this.accountNonExpired = null;
        this.accountNonLocked = null;
        this.credentialsNonExpired = null;
        this.authorities = null;
        this.googleSignIn = null;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder extends PortalUser.Builder<Builder>  {

        private Boolean accountNonExpired = true;
        private Boolean accountNonLocked = true;
        private Boolean credentialsNonExpired = true;
        private Boolean googleSignIn = false;

        private Set<GrantedAuthority> authorities = Collections.<GrantedAuthority>emptySet();
        
        public Builder setAccountNonExpired(boolean accountNonExpired) {
            this.accountNonExpired = accountNonExpired;
            return this;
        }

        public Builder setAccountNonLocked(boolean accountNonLocked) {
            this.accountNonLocked = accountNonLocked;
            return this;
        }

        public Builder setCredentialsNonExpired(boolean credentialsNonExpired) {
            this.credentialsNonExpired = credentialsNonExpired;
            return this;
        }

        public Builder setAuthorities(Set<GrantedAuthority> authorities) {
            this.authorities = authorities;
            return this;
        }

        public Builder setGoogleSignIn(Boolean googleSignIn) {
            this.googleSignIn = googleSignIn;
            return this;
        }

        public Builder from(PortalUser source) {
            super.from(source);
            return this;
        }

        public Builder from(PortalUserDetails source) {
            super.from(source);
            this.accountNonExpired = source.accountNonExpired;
            this.accountNonLocked = source.accountNonLocked;
            this.credentialsNonExpired = source.credentialsNonExpired;
            this.authorities = source.authorities;
            this.googleSignIn = source.googleSignIn;
            return this;
        }

        @Override
        public PortalUserDetails build() {
            return new PortalUserDetails(id, email, firstName, lastName, password, accessToken, role, activated,
                    accountNonExpired, accountNonLocked, credentialsNonExpired, authorities, googleSignIn);
        }
    }

}

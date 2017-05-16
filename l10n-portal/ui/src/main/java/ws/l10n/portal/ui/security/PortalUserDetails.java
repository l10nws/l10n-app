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
    private final Boolean enabled;
    private final Boolean googleSignIn;

    public PortalUserDetails(Integer id, String email, String firstName, String lastName, String password, String accessToken, Role role,
                             Boolean accountNonExpired, Boolean accountNonLocked,
                             Boolean credentialsNonExpired, Boolean enabled, Set<GrantedAuthority> authorities, Boolean googleSignIn) {
        super(id, email, firstName, lastName, password, accessToken, role);
        this.accountNonExpired = accountNonExpired;
        this.accountNonLocked = accountNonLocked;
        this.credentialsNonExpired = credentialsNonExpired;
        this.enabled = enabled;
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
        return enabled;
    }

    public boolean getGoogleSignIn() {
        return googleSignIn;
    }

    public PortalUserDetails() {
        super(null, null, null, null, null, null, null);
        this.accountNonExpired = null;
        this.accountNonLocked = null;
        this.credentialsNonExpired = null;
        this.enabled = null;
        this.authorities = null;
        this.googleSignIn = null;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder extends PortalUser.Builder  {

        private Boolean accountNonExpired = true;
        private Boolean accountNonLocked = true;
        private Boolean credentialsNonExpired = true;
        private Boolean enabled = true;
        private Boolean googleSignIn = false;

        private Set<GrantedAuthority> authorities = Collections.<GrantedAuthority>emptySet();

        @Override
        public Builder setId(Integer id) {
            this.id = id;
            return this;
        }

        @Override
        public Builder setEmail(String email) {
            this.email = email;
            return this;
        }

        @Override
        public Builder setFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        @Override
        public Builder setLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        @Override
        public Builder setPassword(String password) {
            this.password = password;
            return this;
        }

        @Override
        public Builder setAccessToken(String accessToken) {
            this.accessToken = accessToken;
            return this;
        }

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

        public Builder setEnabled(boolean enabled) {
            this.enabled = enabled;
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
            this.enabled = source.enabled;
            this.authorities = source.authorities;
            this.googleSignIn = source.googleSignIn;
            return this;
        }

        @Override
        public PortalUserDetails build() {
            return new PortalUserDetails(id, email, firstName, lastName, password, accessToken, role,
                    accountNonExpired, accountNonLocked, credentialsNonExpired, enabled, authorities, googleSignIn);
        }
    }

}

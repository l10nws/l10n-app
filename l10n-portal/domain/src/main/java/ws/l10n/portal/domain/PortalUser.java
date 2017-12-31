package ws.l10n.portal.domain;


/**
 * @author Sergey Boguckiy
 * @author Anton Mokshyn
 */
public class PortalUser {

    private Integer id;
    private String email;
    private String firstName;
    private String lastName;
    private String password;
    private String accessToken;
    private Role role;
    private Boolean activated;

    public PortalUser() {

    }

    public PortalUser(Integer id, String email, String firstName, String lastName, String password, String accessToken, Role role, Boolean activated) {
        this.id = id;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.accessToken = accessToken;
        this.role = role;
        this.activated = activated;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public static Builder builder() {
        return new Builder();
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Role getRole() {
        return role;
    }

    public Boolean isActivated() {
        return activated;
    }

    public void setActivated(Boolean activated) {
        this.activated = activated;
    }
    
    @SuppressWarnings("unchecked")
    public static class Builder<T extends Builder<T>> {

        protected Integer id;
        protected String email;
        protected String firstName;
        protected String lastName;
        protected String password;
        protected String accessToken;
        protected Role role;
        protected Boolean activated;
        
        public T setRole(Role role) {
            this.role = role;
            return (T) this;
        }

        public T setId(Integer id) {
            this.id = id;
            return (T) this;
        }

        public T setEmail(String email) {
            this.email = email;
            return (T) this;
        }

        public T setFirstName(String firstName) {
            this.firstName = firstName;
            return  (T) this;
        }

        public T setLastName(String lastName) {
            this.lastName = lastName;
            return (T) this;
        }

        public T setPassword(String password) {
            this.password = password;
            return (T) this;
        }

        public T setAccessToken(String accessToken) {
            this.accessToken = accessToken;
            return (T) this;
        }

        public T setActivated(Boolean activated) {
            this.activated = activated;
            return (T) this;
        }

        public T from(PortalUser source) {
            this.id = source.getId();
            this.email = source.getEmail();
            this.firstName = source.getFirstName();
            this.lastName = source.getLastName();
            this.password = source.getPassword();
            this.accessToken = source.getAccessToken();
            this.role = source.getRole();
            this.activated = source.isActivated();
            return (T) this;
        }

        public PortalUser build() {
            return new PortalUser(id, email, firstName, lastName, password, accessToken, role, activated);
        }
    }

}

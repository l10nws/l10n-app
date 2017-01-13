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

    public PortalUser() {

    }

    public PortalUser(Integer id, String email, String firstName, String lastName, String password, String accessToken, Role role) {
        this.id = id;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.accessToken = accessToken;
        this.role = role;
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

    public static class Builder {

        protected Integer id;
        protected String email;
        protected String firstName;
        protected String lastName;
        protected String password;
        protected String accessToken;
        protected Role role;

        public Builder setRole(Role role) {
            this.role = role;
            return this;
        }

        public Builder setId(Integer id) {
            this.id = id;
            return this;
        }

        public Builder setEmail(String email) {
            this.email = email;
            return this;
        }

        public Builder setFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder setLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder setPassword(String password) {
            this.password = password;
            return this;
        }

        public Builder setAccessToken(String accessToken) {
            this.accessToken = accessToken;
            return this;
        }

        public Builder from(PortalUser source) {
            this.id = source.getId();
            this.email = source.getEmail();
            this.firstName = source.getFirstName();
            this.lastName = source.getLastName();
            this.password = source.getPassword();
            this.accessToken = source.getAccessToken();
            this.role = source.getRole();
            return this;
        }

        public PortalUser build() {
            return new PortalUser(id, email, firstName, lastName, password, accessToken, role);
        }
    }

}

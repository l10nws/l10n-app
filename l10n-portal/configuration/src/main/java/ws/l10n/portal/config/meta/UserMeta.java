package ws.l10n.portal.config.meta;

import java.io.Serializable;

/**
 * @author Serhii Bohutskyi
 */
public class UserMeta implements Serializable {

    private String email;
    private String password;

    public UserMeta(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "UserMeta{" +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}

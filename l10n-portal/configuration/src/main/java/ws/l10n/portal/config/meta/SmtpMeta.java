package ws.l10n.portal.config.meta;

import java.io.Serializable;

/**
 * @author Serhii Bohutskyi
 */
public class SmtpMeta implements Serializable {

    private String host;
    private Integer port;
    private String username;
    private String password;
    private boolean enable = false;
    private boolean emailAccountActivationEnabled = false;

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEmailAccountActivationEnabled() {
        return emailAccountActivationEnabled;
    }

    public void setEmailAccountActivationEnabled(boolean emailAccountActivationEnabled) {
        this.emailAccountActivationEnabled = emailAccountActivationEnabled;
    }

    @Override
    public String toString() {
        return "SmtpMeta{" +
                "host='" + host + '\'' +
                ", port=" + port +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", enable=" + enable +
                ", emailAccountActivationEnabled=" + emailAccountActivationEnabled +
                '}';
    }
}

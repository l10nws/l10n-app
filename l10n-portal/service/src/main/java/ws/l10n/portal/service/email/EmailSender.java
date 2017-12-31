package ws.l10n.portal.service.email;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Component
public class EmailSender {

    private String from = "no-reply@mail.l10n.ws";

    @Value("${l10n.mail.smtp.port}")
    private String port;
    @Value("${l10n.mail.smtp.host}")
    private String host;
    @Value("${l10n.mail.smtp.username}")
    private String userName;
    @Value("${l10n.mail.smtp.password}")
    private String password;

    @PostConstruct
    public void init() {
        Properties properties = System.getProperties();
        properties.setProperty("mail.smtp.host", host);
        properties.setProperty("mail.smtp.port", port);
        properties.setProperty("mail.smtp.starttls.enable", "true");
        properties.setProperty("mail.smtp.auth", "true");
    }

    public void sendEmail(String emailAddress, String body, String subject) {
        try {
            Session session = Session.getInstance(System.getProperties());
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            Address[] recipientAddress = {new InternetAddress(emailAddress)};
            message.setRecipients(Message.RecipientType.TO, recipientAddress);
            message.setSubject(subject);
            message.setContent(body, "text/html; charset=utf-8");

            Transport.send(message, message.getAllRecipients(), userName, password);
        } catch (MessagingException ex) {
            throw new RuntimeException("Exception caught when sending an activation email", ex);
        }
    }
}

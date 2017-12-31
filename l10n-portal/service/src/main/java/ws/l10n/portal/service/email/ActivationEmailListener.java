package ws.l10n.portal.service.email;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import ws.l10n.portal.service.event.PortalUserRegisteredEvent;

import java.io.StringWriter;

@Component
public class ActivationEmailListener {

    @Autowired
    private EmailSender emailSender;
    @Autowired
    private VelocityEngine velocityEngine;

    @EventListener
    @Async
    public void sendActivationEmail(PortalUserRegisteredEvent userRegisteredEvent) {
        String email = userRegisteredEvent.getEmail();
        String activationToken = userRegisteredEvent.getActivationToken();
        String appUrl = userRegisteredEvent.getAppUrl();

        String emailBody = constructActivationEmailBody(activationToken, email, appUrl);
        emailSender.sendEmail(email, emailBody, "Activate account on l10n.ws");
    }

    private String constructActivationEmailBody(String activationToken, String email, String appUrl) {
        Template template = velocityEngine.getTemplate("velocity/activation-email.vm");

        VelocityContext context = new VelocityContext();
        context.put("token", activationToken);
        context.put("email", email);
        context.put("appUrl", appUrl);

        StringWriter writer = new StringWriter();
        template.merge(context, writer);

        return writer.toString();
    }
}

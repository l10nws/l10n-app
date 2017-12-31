package ws.l10n.portal.service.email;

import org.apache.velocity.app.VelocityEngine;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ws.l10n.portal.service.event.PortalUserRegisteredEvent;

import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.validateMockitoUsage;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class ActivationEmailListenerTest {

    private static final String EMAIL = "email@email.com";
    private static final String TOKEN = "ACTIVATION_TOKEN";
    private static final String URL = "https://l10n.ws:8080/ui";
    
    @Mock
    private EmailSender mockEmailSender;

    @Spy
    private VelocityEngine velocityEngine;

    @InjectMocks
    private ActivationEmailListener activationEmailListener = new ActivationEmailListener();

    @Before
    public void setUp() throws Exception {
        velocityEngine.setProperty("resource.loader","class");
        velocityEngine.setProperty("class.resource.loader.class","org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        velocityEngine.init();
        MockitoAnnotations.initMocks(ActivationEmailListener.class);
    }

    @Test
    public void testConstructsEmailWithToken() throws Exception {
        activationEmailListener.sendActivationEmail(new PortalUserRegisteredEvent(TOKEN, EMAIL, URL));
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);

        verify(mockEmailSender).sendEmail(any(String.class), captor.capture(), any(String.class));

        assertTrue("expected to find token in email: " + captor.getValue(), captor.getValue().contains(TOKEN));
    }

    @Test
    public void testConstructsEmailWithUserEmail() throws Exception {
        activationEmailListener.sendActivationEmail(new PortalUserRegisteredEvent(TOKEN, EMAIL, URL));
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);

        verify(mockEmailSender).sendEmail(any(String.class), captor.capture(), any(String.class));

        assertTrue("expected to find user email in email: " + captor.getValue(), captor.getValue().contains(EMAIL));
    }

    @Test
    public void testConstructsEmailWithLinkToUrl() throws Exception {
        activationEmailListener.sendActivationEmail(new PortalUserRegisteredEvent(TOKEN, EMAIL, URL));
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);

        verify(mockEmailSender).sendEmail(any(String.class), captor.capture(), any(String.class));

        assertTrue("expected to find url in email: " + captor.getValue(), captor.getValue().contains(URL));
    }
}
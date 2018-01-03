package ws.l10n.portal.config.meta;

import org.junit.Assert;
import org.junit.Test;
import org.xml.sax.SAXException;
import ws.l10n.portal.config.PortalConfigReader;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author Serhii Bohutskyi
 */
public class PortalConfigReaderTest {

    private final PortalConfigReader reader = new PortalConfigReader();

    @Test
    public void positiveReadTest() throws ParserConfigurationException, SAXException, XPathExpressionException, IOException {

        InputStream stream = PortalConfigReaderTest.class.getResourceAsStream("/positive-config.xml");
        ConfigMeta configMeta = reader.read(stream);
        stream.close();

        Assert.assertNotNull(configMeta);

        Assert.assertNotNull(configMeta.getSuperusers());
        Assert.assertEquals("admin@mail.com", configMeta.getSuperusers().get(0).getEmail());
        Assert.assertEquals("admin", configMeta.getSuperusers().get(0).getPassword());

        Assert.assertNotNull(configMeta.getSmtp());
        Assert.assertTrue(configMeta.getSmtp().isEmailAccountActivationEnabled());
        Assert.assertEquals("localhost", configMeta.getSmtp().getHost());
        Assert.assertEquals(587, configMeta.getSmtp().getPort().longValue());
        Assert.assertEquals("admin", configMeta.getSmtp().getUsername());
        Assert.assertEquals("admin-password", configMeta.getSmtp().getPassword());
        Assert.assertEquals(true, configMeta.getSmtp().isEnable());

        Assert.assertNotNull(configMeta.getMaillist());
        Assert.assertEquals("noreply@yourcompany.com", configMeta.getMaillist().get("noreply"));

        Assert.assertNotNull(configMeta.getDatasource());
        Assert.assertEquals("jdbc:postgresql://localhost:5432/l10n-portal", configMeta.getDatasource().getUrl());
        Assert.assertEquals("postgres", configMeta.getDatasource().getUsername());
        Assert.assertEquals("1", configMeta.getDatasource().getPassword());
        Assert.assertEquals("org.postgresql.Driver", configMeta.getDatasource().getDriverClassName());
    }

    @Test
    public void readWithDefaultValues() throws ParserConfigurationException, SAXException, XPathExpressionException, IOException {

        InputStream stream = PortalConfigReaderTest.class.getResourceAsStream("/default-value-config.xml");
        ConfigMeta configMeta = reader.read(stream);
        stream.close();

        Assert.assertNotNull(configMeta);

        Assert.assertNotNull(configMeta.getSuperusers());
        Assert.assertEquals( 0, configMeta.getSuperusers().size());

        Assert.assertNotNull(configMeta.getSmtp());
        Assert.assertEquals("", configMeta.getSmtp().getHost());
        Assert.assertNull(configMeta.getSmtp().getPort());
        Assert.assertEquals("", configMeta.getSmtp().getUsername());
        Assert.assertEquals("", configMeta.getSmtp().getPassword());
        Assert.assertEquals(false, configMeta.getSmtp().isEnable());

        Assert.assertNotNull(configMeta.getMaillist());
        Assert.assertEquals("noreply@yourcompany.com", configMeta.getMaillist().get("noreply"));

        Assert.assertNotNull(configMeta.getDatasource());
        Assert.assertEquals("jdbc:postgresql://localhost:5432/l10n-portal", configMeta.getDatasource().getUrl());
        Assert.assertEquals("postgres", configMeta.getDatasource().getUsername());
        Assert.assertEquals("1", configMeta.getDatasource().getPassword());
        Assert.assertEquals("org.postgresql.Driver", configMeta.getDatasource().getDriverClassName());
    }

    @Test
    public void emptyValuesConfig() throws ParserConfigurationException, SAXException, XPathExpressionException, IOException {

        InputStream stream = PortalConfigReaderTest.class.getResourceAsStream("/trim-value-config.xml");
        ConfigMeta configMeta = reader.read(stream);
        stream.close();

        Assert.assertNotNull(configMeta);

        Assert.assertNotNull(configMeta.getSuperusers());
        Assert.assertEquals("admin@mail.com", configMeta.getSuperusers().get(0).getEmail());
        Assert.assertEquals("admin", configMeta.getSuperusers().get(0).getPassword());

        Assert.assertNotNull(configMeta.getSmtp());
        Assert.assertEquals("localhost", configMeta.getSmtp().getHost());
        Assert.assertEquals(587, configMeta.getSmtp().getPort().longValue());
        Assert.assertEquals("admin", configMeta.getSmtp().getUsername());
        Assert.assertEquals("admin-password", configMeta.getSmtp().getPassword());
        Assert.assertEquals(true, configMeta.getSmtp().isEnable());

        Assert.assertNotNull(configMeta.getMaillist());
        Assert.assertEquals("noreply@yourcompany.com", configMeta.getMaillist().get("noreply"));

        Assert.assertNotNull(configMeta.getDatasource());
        Assert.assertEquals("jdbc:postgresql://localhost:5432/l10n-portal", configMeta.getDatasource().getUrl());
        Assert.assertEquals("postgres", configMeta.getDatasource().getUsername());
        Assert.assertEquals("1", configMeta.getDatasource().getPassword());
        Assert.assertEquals("org.postgresql.Driver", configMeta.getDatasource().getDriverClassName());
    }

}

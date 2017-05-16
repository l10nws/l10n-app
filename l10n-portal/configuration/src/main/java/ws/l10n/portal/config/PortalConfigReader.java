package ws.l10n.portal.config;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import ws.l10n.portal.config.meta.ConfigMeta;
import ws.l10n.portal.config.meta.DatasourceMeta;
import ws.l10n.portal.config.meta.SmtpMeta;
import ws.l10n.portal.config.meta.UserMeta;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 */
public class PortalConfigReader {

    private static final Logger LOGGER = LoggerFactory.getLogger(PortalConfigReader.class);

    public ConfigMeta read(final InputStream configStream) throws ParserConfigurationException, IOException, SAXException, XPathExpressionException {

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(configStream);
        XPathFactory xPathfactory = XPathFactory.newInstance();
        XPath xpath = xPathfactory.newXPath();

        List<UserMeta> superusers = getSuperusers(doc, xpath);
        SmtpMeta smtpMeta = getSmtp(doc, xpath);
        DatasourceMeta datasourceMeta = getDatasource(doc, xpath);
        Map<String, String> maillist = getMaillist(doc, xpath);

        ConfigMeta configMeta = new ConfigMeta();
        configMeta.setSuperusers(superusers);
        configMeta.setSmtp(smtpMeta);
        configMeta.setDatasource(datasourceMeta);
        configMeta.setMaillist(maillist);

        return configMeta;
    }

    private Map<String, String> getMaillist(Document doc, XPath xpath) throws XPathExpressionException {
        LOGGER.debug("Reading <mail-list>...");

        Map<String, String> maillist = new HashMap<>();
        NodeList nl = (NodeList) xpath.compile("/configuration/mail-list").evaluate(doc, XPathConstants.NODESET);
        if (nl == null) {
            LOGGER.warn("<mail-list> NOT found in configuration!");
            return maillist;
        }
        for (int i = 0; i < nl.getLength(); i++) {
            Node item = nl.item(i);

            String name = getString(xpath.compile("item/@name").evaluate(item));
            String value = getString(xpath.compile("item").evaluate(item));

            boolean isValid = true;
            if (StringUtils.isEmpty(name)) {
                LOGGER.warn("<name> is empty in <mail-list>! Skipping");
                isValid = false;
            }
            if (StringUtils.isEmpty(value)) {
                LOGGER.warn("Value of <item> is empty in <mail-list>! Skipping");
                isValid = false;
            }
            if (isValid) {
                maillist.put(name, value);
                LOGGER.info("Maillist item name [{}], value [{}] added.", name, value);
            }
        }
        return maillist;
    }

    private DatasourceMeta getDatasource(Document doc, XPath xpath) throws XPathExpressionException {
        LOGGER.debug("Reading <datasource> ...");

        Node node = (Node) xpath.compile("/configuration/datasource").evaluate(doc, XPathConstants.NODE);
        if (node == null) {
            LOGGER.warn("<datasource> NOT found in configuration!");
            return null;
        }
        String driverClassName = getString(xpath.compile("driverClassName").evaluate(node, XPathConstants.STRING));
        String url = getString(xpath.compile("url").evaluate(node, XPathConstants.STRING));
        String username = getString(xpath.compile("username").evaluate(node, XPathConstants.STRING));
        String password = getString(xpath.compile("password").evaluate(node, XPathConstants.STRING));

        if (StringUtils.isEmpty(url)) {
            LOGGER.warn("Datasource <url> is empty in <datasource>!");
        }

        if (StringUtils.isEmpty(username)) {
            LOGGER.warn("Datasource <username> is empty in <datasource>!");
        }

        if (password == null) {
            password = "";
            LOGGER.debug("Datasource <password> is empty in <datasource>! Set default empty string");
        }

        if (StringUtils.isEmpty(driverClassName)) {
            driverClassName = "org.postgresql.Driver";
            LOGGER.debug("Datasource <driverClassName> is empty in <datasource>! Set default [org.postgresql.Driver].");
        }

        LOGGER.info("Datasource configuration read, \n" +
                        "<url> [{}]\n" +
                        "<username> [{}]\n" +
                        "<password> [{}]\n" +
                        "<driverClassName> [{}]",
                url, username, password, driverClassName);

        return new DatasourceMeta(driverClassName.trim(), url.trim(), username.trim(), password.trim());
    }

    private SmtpMeta getSmtp(Document doc, XPath xpath) throws XPathExpressionException {
        Node node = (Node) xpath.compile("/configuration/smtp").evaluate(doc, XPathConstants.NODE);

        if (node == null) {
            LOGGER.warn("<smtp> not found in configuration!");
            return null;
        }

        String host = getString(xpath.compile("host").evaluate(node, XPathConstants.STRING));
        String port = getString(xpath.compile("port").evaluate(node, XPathConstants.STRING));
        String username = getString(xpath.compile("username").evaluate(node, XPathConstants.STRING));
        String password = getString(xpath.compile("password").evaluate(node, XPathConstants.STRING));
        String enable = getString(xpath.compile("@enable").evaluate(node, XPathConstants.STRING));

        SmtpMeta smtpMeta = new SmtpMeta();

        if (enable != null) {
            LOGGER.info("Smtp enable is [{}]!", enable);
            smtpMeta.setEnable(Boolean.valueOf(enable));
        }

        if (StringUtils.isEmpty(host)) {
            LOGGER.warn("<host> is empty in <smtp>!");
        }
        smtpMeta.setHost(host);

        if (!NumberUtils.isNumber(port)) {
            LOGGER.warn("<port> is not a number in <smtp>!");
        } else {
            smtpMeta.setPort(Integer.valueOf(port));
        }

        if (StringUtils.isEmpty(username)) {
            LOGGER.warn("<username> is empty in <smtp>!");
        }
        smtpMeta.setUsername(username);


        if (StringUtils.isEmpty(password)) {
            LOGGER.warn("<password> is empty in <smtp>!");
        }
        smtpMeta.setPassword(password);

        return smtpMeta;
    }

    private String getString(Object result) {
        if (result != null) {
            return ((String) result).trim();
        } else {
            return null;
        }
    }

    private List<UserMeta> getSuperusers(Document doc, XPath xpath) throws XPathExpressionException {
        LOGGER.debug("Reading superusers...");
        List<UserMeta> users = new ArrayList<>();
        NodeList nl = (NodeList) xpath.compile("/configuration/superusers").evaluate(doc, XPathConstants.NODESET);
        if (nl == null) {
            LOGGER.warn("<superusers> NOT found in configuration!");
            return users;
        }
        for (int i = 0; i < nl.getLength(); i++) {
            Node item = nl.item(i);
            String email = getString(xpath.compile("user/email").evaluate(item, XPathConstants.STRING));
            String password = getString(xpath.compile("user/password").evaluate(item, XPathConstants.STRING));

            boolean isValid = true;
            if (StringUtils.isEmpty(email)) {
                LOGGER.warn("<email> is empty in <superusers>! Skipping");
                isValid = false;
            }
            if (StringUtils.isEmpty(password)) {
                LOGGER.warn("<password> is empty in <superusers>! Skipping");
                isValid = false;
            }
            if (isValid) {
                LOGGER.debug("Superuser read, <email> [{}], password [{}]", email, password);
                users.add(new UserMeta(email, password));
            }
        }
        return users;
    }
}

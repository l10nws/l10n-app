package ws.l10n.portal.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;
import ws.l10n.portal.config.meta.ConfigMeta;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author Serhii Bohutskyi
 */
public class PortalConfigLoader {
    private static final Logger LOGGER = LoggerFactory.getLogger(PortalConfigLoader.class);

    private static final String FILE_NAME = "l10n-config.xml";
    private static final String SYS_OPTION_NAME = "L10N_CONFIG_DIR";
    private static final String FILE_SEPARATOR = System.getProperty("file.separator");

    private PortalConfigReader configReader;
    private PortalConfigValidator portalConfigValidator;

    public PortalConfigLoader(PortalConfigReader configReader, PortalConfigValidator portalConfigValidator) {
        this.configReader = configReader;
        this.portalConfigValidator = portalConfigValidator;
    }

    public ConfigMeta load() {
        InputStream configStream = null;
        try {
            configStream = resolveConfigStream();
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Portal configuration file not found [" + FILE_NAME + "]", e);
        }
        try {
            ConfigMeta configMeta = configReader.read(configStream);
            configStream.close();

            portalConfigValidator.validate(configMeta);

            return configMeta;
        } catch (ParserConfigurationException | XPathExpressionException | SAXException e) {
            throw new RuntimeException("Portal configuration cannot be parsed!", e);
        } catch (IOException e) {
            throw new RuntimeException("Portal configuration cannot be found or read!", e);
        }
    }

    private InputStream resolveConfigStream() throws FileNotFoundException {
        String systemProperty = null;
        try {
            systemProperty = System.getProperty(SYS_OPTION_NAME);
        } catch (Exception e) {
            LOGGER.warn("Have NO access to system property! Check permissions");
        }
        String systemVariable = System.getenv(SYS_OPTION_NAME);

        String configDir = systemProperty == null ? systemVariable : systemProperty;

        LOGGER.info("Config directory is [{}], [{}] from system variable [{}], system property [{}]",
                configDir, SYS_OPTION_NAME, systemVariable, systemProperty);
        String filePath = null;
        InputStream stream = null;
        if (configDir != null) {
            if (configDir.lastIndexOf(FILE_SEPARATOR) == configDir.length()) {
                filePath = configDir + FILE_NAME;
                stream = new FileInputStream(filePath);
            } else {
                filePath = configDir + FILE_SEPARATOR + FILE_NAME;
                stream = new FileInputStream(filePath);
            }
        } else {
            filePath = PortalConfigLoader.class.getResource("/" + FILE_NAME).getPath();
            stream = PortalConfigLoader.class.getResourceAsStream("/" + FILE_NAME);
        }
        LOGGER.info("Portal configuration file is [{}]", filePath);
        return stream;
    }
}

package ws.l10n.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ws.l10n.common.logging.LoggerUtils;
import ws.l10n.portal.domain.Message;
import ws.l10n.portal.domain.Version;
import ws.l10n.portal.repository.LocaleVersionRepository;
import ws.l10n.portal.repository.MessageRepository;
import ws.l10n.portal.repository.VersionRepository;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * @author Sergey Boguckiy
 */
@RestController
@RequestMapping("/v1")
public class V1Controller {

    private static final String DEFAULT_LOCALE = "defaultLocale";
    private static final String CONTENT = "content";
    private static final String SUPPORTED_LOCALES = "supportedLocales";
    private static final String LOCALE = "locale";
    private static final String MESSAGES = "messages";
    private static final String KEY = "key";
    private static final String VALUE = "value";

    private static final Logger LOGGER = LoggerUtils.getLogger();

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private LocaleVersionRepository localeVersionRepository;
    @Autowired
    private VersionRepository bundleVersionRepository;
    @Value("${build.version}")
    private String version;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String version() {
        return "{\"buildVersion\": " + version + "}";
    }

    @RequestMapping("/message")
    public void handle(@RequestParam("bundleUid") String bundleUid,
                       @RequestParam(value = "locale[]", required = false) String[] locale,
                       @RequestParam(value = "version") String version,
                       HttpServletResponse response) throws IOException {
        handleRequest(bundleUid, locale, version, response);
    }

    @RequestMapping(value = "/m", method = RequestMethod.GET)
    public void handleWithShort(@RequestParam("b") String bundleUid,
                                @RequestParam(value = "l[]", required = false) String[] l,
                                @RequestParam(value = "v") String v,
                                HttpServletResponse response) throws IOException {
        handleRequest(bundleUid, l, v, response);
    }


    public void handleRequest(String bundleUid, String[] localeIds, String versionName, HttpServletResponse response) throws IOException {

        final PrintWriter writer = response.getWriter();

        Version version = bundleVersionRepository.getByBundleUidAndVersionName(bundleUid, versionName);

        if (version == null) {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            OBJECT_MAPPER.writeValue(response.getWriter(), new ErrorResponse(ApiResponseStatus.BAD_PARAMETER,
                    "no message found for Bundle UID '" + bundleUid + "' and Version '" + versionName + "'"));
            return;
        }

        writer.write("{ \"" + DEFAULT_LOCALE + "\" : \"" + version.getDefaultLocaleId() + "\"");

        List<String> supportedLocales = null;
        if (localeIds == null) {
            supportedLocales = localeVersionRepository.getLocaleIdsByVersionId(version.getId());
            localeIds = supportedLocales.toArray(new String[supportedLocales.size()]);
        } else {
            supportedLocales = localeVersionRepository.getLocaleIdsByVersionId(version.getId());
        }
        writer.write(",\"" + SUPPORTED_LOCALES + "\": " + OBJECT_MAPPER.writeValueAsString(supportedLocales));

        writer.write(",\"" + CONTENT + "\": [");

        for (int i = 0; i < localeIds.length; i++) {

            String localeId = localeIds[i];

            writer.write("{\"" + LOCALE + "\":\"" + localeId + "\",");
            writer.write("\"" + MESSAGES + "\":[");

            List<Message> messages = messageRepository.findByVersionIdAndLocaleId(version.getId(), localeId);
            String separator = "";
            for (Message message : messages) {  // TODO lazy loading
                writer.write(separator);
                writer.write("{\"" + KEY + "\":" + escape(message.getKey()) + ",\"" + VALUE + "\":" + escape(message.getValue()) + "}");
                separator = ",";
            }
            writer.write("]}");
            writer.flush();

            if (i != (localeIds.length - 1)) {
                writer.write(",");
            }
        }

        writer.write("]");
        writer.write("}");

        writer.flush();
    }

    private String escape(String string) {
        if (string == null || string.length() == 0) {
            return "\"\"";
        }
        char c = 0;
        int i;
        int len = string.length();
        StringBuilder sb = new StringBuilder(len + 4);
        String t;
        sb.append('"');
        for (i = 0; i < len; i += 1) {
            c = string.charAt(i);
            switch (c) {
                case '\\':
                case '"':
                    sb.append('\\');
                    sb.append(c);
                    break;
                case '/':
                    if (i > 0 && string.charAt(i - 1) == '<') {
                        sb.append('\\');
                    }
                    sb.append(c);
                    break;
                case '\b':
                    sb.append("\\b");
                    break;
                case '\t':
                    sb.append("\\t");
                    break;
                case '\n':
                    sb.append("\\n");
                    break;
                case '\f':
                    sb.append("\\f");
                    break;
                case '\r':
                    sb.append("\\r");
                    break;
                default:
                    if (c < ' ') {
                        t = "000" + Integer.toHexString(c);
                        sb.append("\\u" + t.substring(t.length() - 4));
                    } else {
                        sb.append(c);
                    }
            }
        }
        sb.append('"');
        return sb.toString();
    }


}

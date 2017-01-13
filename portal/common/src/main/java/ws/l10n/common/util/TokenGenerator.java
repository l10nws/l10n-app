package ws.l10n.common.util;

import org.slf4j.Logger;
import ws.l10n.common.logging.LoggerUtils;

import java.math.BigInteger;

/**
 * @author Sergey Boguckiy
 */
public class TokenGenerator {

    private static final Logger LOGGER = LoggerUtils.getLogger();

    private static final String SALT = "79244584";

    private final BaseX baseX = new BaseX();

    public String generate(Integer fromId) {
        String result = baseX.encode(new BigInteger(fromId + SALT));
        LOGGER.debug("generated token [{}] from number [{}]", result, fromId);
        return result;
    }

    public String generate() {
        return baseX.encode(BigInteger.valueOf(System.currentTimeMillis()));
    }

}

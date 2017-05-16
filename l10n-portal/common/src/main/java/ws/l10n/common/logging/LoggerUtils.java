package ws.l10n.common.logging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Anton Mokshyn.
 */
public class LoggerUtils {

    public static Logger getLogger() {
        Throwable throwable = new Throwable();
        throwable.fillInStackTrace();
        return LoggerFactory.getLogger(throwable.getStackTrace()[1].getClassName());
    }

    private LoggerUtils() {
    }
}

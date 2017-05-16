package ws.l10n.portal.ui.util;

import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author Sergey Boguckiy
 */
public class Functions {

    public static String year() {
        return new SimpleDateFormat("yyyy").format(new Date());
    }

    public static boolean contains(List list, Object o) {
        return list.contains(o);
    }

    public static String join(List list, String separator) {
        return StringUtils.join(list, separator);
    }
}

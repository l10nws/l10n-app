
package ws.l10n.portal.ui.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author Anton Mokshyn
 */
@Controller
@RequestMapping(value = "/developer")
public class DevController {

    @RequestMapping(value = "/doc/http", method = RequestMethod.GET)
    public String doc() {
        return "http_api_doc";
    }
}

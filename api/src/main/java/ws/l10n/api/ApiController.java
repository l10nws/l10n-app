package ws.l10n.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Serhii_Bohutskyi
 */
@RestController
@RequestMapping("/")
public class ApiController {

    @RequestMapping(method = RequestMethod.GET)
    public String root() {
        return "{ \"availableVersions\" : [\"v1\"] }";
    }

}

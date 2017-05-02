package ws.l10n.api.v1;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import ws.l10n.api.validator.MessageValidator;
import ws.l10n.common.logging.LoggerUtils;
import ws.l10n.portal.domain.Message;

import javax.validation.Valid;

/**
 * @author Serhii Bohutksyi
 */
@RestController
@RequestMapping("/v1")
public class MessageController {
    private static final Logger LOGGER = LoggerUtils.getLogger();

    @Autowired
    private MessageValidator messageValidator;

    @InitBinder
    private void initBinder(WebDataBinder binder) {
        binder.setValidator(messageValidator);
    }

    @RequestMapping(value = "/message", method = RequestMethod.PUT)
    public void save(@Valid @RequestBody Message message){


    }

}

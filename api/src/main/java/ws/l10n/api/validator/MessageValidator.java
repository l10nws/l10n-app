package ws.l10n.api.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import ws.l10n.portal.domain.Message;

/**
 * @author Serhii Bohutksyi
 */
@Component
public class MessageValidator implements Validator {
    public boolean supports(Class<?> clazz) {
        return Message.class.equals(clazz);
    }

    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmpty(errors, "versionId", "versionId.empty");
        ValidationUtils.rejectIfEmpty(errors, "localeId", "localeId.empty");
        ValidationUtils.rejectIfEmpty(errors, "key", "key.empty");
        ValidationUtils.rejectIfEmpty(errors, "value", "value.empty");
    }
}

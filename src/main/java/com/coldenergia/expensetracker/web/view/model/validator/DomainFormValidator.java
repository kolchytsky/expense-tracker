package com.coldenergia.expensetracker.web.view.model.validator;

import com.coldenergia.expensetracker.web.view.model.domain.DomainForm;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import static com.coldenergia.expensetracker.validator.RegexPatterns.ALPHANUMERIC_AND_UNDERSCORES_ONLY;
import static org.springframework.validation.ValidationUtils.rejectIfEmpty;

/**
 * This class duplicates much of the validation declared in
 * {@link com.coldenergia.expensetracker.validator.DomainValidator}. However, I find it justified
 * to have validation at both service and controller level. Also, as the validation in service
 * level is covered with tests, this validation is not.<br>
 * User: coldenergia
 * Date: 5/24/14
 * Time: 3:08 PM
 */
@Component
public class DomainFormValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return DomainForm.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        DomainForm domainForm = (DomainForm) target;
        rejectIfEmpty(errors, "name", "domain.name.empty");
        String name = domainForm.getName();
        if (name != null) {
            if (name.length() > 40) {
                errors.rejectValue("name", "domain.name.too.long");
            }
            if (!name.matches(ALPHANUMERIC_AND_UNDERSCORES_ONLY)) {
                errors.rejectValue("name", "domain.name.contains.special.chars");
            }
        }
    }

}

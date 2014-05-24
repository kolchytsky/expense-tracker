package com.coldenergia.expensetracker.validator;

import com.coldenergia.expensetracker.domain.Domain;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import static com.coldenergia.expensetracker.validator.RegexPatterns.ALPHANUMERIC_AND_UNDERSCORES_ONLY;

/**
 * User: coldenergia
 * Date: 5/24/14
 * Time: 10:31 AM
 */
@Component
public class DomainValidator {

    public ValidationResult validate(Domain domain) {
        ValidationResult result = new ValidationResult();

        String name = domain.getName();
        if (StringUtils.isEmpty(name)) {
            result.rejectValue("name", "domain.name.empty");
        } else {
            if (name.length() > 40) {
                result.rejectValue("name", "domain.name.too.long");
            }
            if (!name.matches(ALPHANUMERIC_AND_UNDERSCORES_ONLY)) {
                result.rejectValue("name", "domain.name.contains.special.chars");
            }
        }

        return result;
    }

}

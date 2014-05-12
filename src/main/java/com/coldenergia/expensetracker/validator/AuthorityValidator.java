package com.coldenergia.expensetracker.validator;

import com.coldenergia.expensetracker.domain.Authority;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import static com.coldenergia.expensetracker.validator.RegexPatterns.ALPHANUMERIC_AND_UNDERSCORES_ONLY;

/**
 * User: coldenergia
 * Date: 5/12/14
 * Time: 8:49 PM
 */
@Component
public class AuthorityValidator {

    public ValidationResult validate(Authority authority) {
        ValidationResult result = new ValidationResult();
        if (StringUtils.isEmpty(authority.getName())) {
            result.rejectValue("name", "authority.name.empty");
        }
        String name = authority.getName();
        if (name != null) {
            if (name.length() > 60) {
                result.rejectValue("name", "authority.name.too.long");
            }
            if (!name.matches(ALPHANUMERIC_AND_UNDERSCORES_ONLY)) {
                result.rejectValue("name", "authority.name.contains.special.chars");
            }
        }
        return result;
    }

}

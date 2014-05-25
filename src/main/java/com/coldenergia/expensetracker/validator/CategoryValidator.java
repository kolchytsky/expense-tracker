package com.coldenergia.expensetracker.validator;

import com.coldenergia.expensetracker.domain.Category;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

/**
 * User: coldenergia
 * Date: 5/25/14
 * Time: 1:03 PM
 */
@Component
public class CategoryValidator {

    public ValidationResult validate(Category category) {
        ValidationResult result = new ValidationResult();

        if (category.getDomain() == null) {
            result.rejectValue("domain", "category.domain.null");
        }

        String name = category.getName();
        if (StringUtils.isEmpty(name)) {
            result.rejectValue("name", "category.name.empty");
        } else if (name.length() > 60) {
            result.rejectValue("name", "category.name.too.long");
        }

        return result;
    }

}

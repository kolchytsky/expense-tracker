package com.coldenergia.expensetracker.validator;

import com.coldenergia.expensetracker.domain.Expense;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

/**
 * User: coldenergia
 * Date: 5/25/14
 * Time: 5:15 PM
 */
@Component
public class ExpenseValidator {

    public ValidationResult validate(Expense expense) {
        ValidationResult result = new ValidationResult();

        String name = expense.getName();
        if (StringUtils.isEmpty(name)) {
            result.rejectValue("name", "expense.name.empty");
        } else if (name.length() > 60) {
            result.rejectValue("name", "expense.name.too.long");
        }

        if (expense.getCategory() == null) {
            result.rejectValue("category", "expense.category.null");
        }

        return result;
    }

}

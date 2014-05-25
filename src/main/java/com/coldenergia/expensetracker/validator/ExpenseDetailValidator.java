package com.coldenergia.expensetracker.validator;

import com.coldenergia.expensetracker.domain.ExpenseDetail;
import com.coldenergia.expensetracker.domain.Unit;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * User: coldenergia
 * Date: 5/25/14
 * Time: 6:17 PM
 */
@Component
public class ExpenseDetailValidator {

    public ValidationResult validate(ExpenseDetail expenseDetail) {
        ValidationResult result = new ValidationResult();

        if (expenseDetail.getExpense() == null) {
            result.rejectValue("expense", "expense.detail.expense.null");
        }

        // Try to determine expense detail type...
        if (expenseDetail.getFullPrice() != null) {
            // Treat as 'Basic' expense type.

            if (hasDetailedTypeAttributesSet(expenseDetail)) {
                result.reject("expense.detail.invalid.type");
            }
        } else {
            // Treat as 'Detailed' expense type.

            BigDecimal pricePerUnit = expenseDetail.getPricePerUnit();
            if (pricePerUnit == null) {
                result.reject("expense.detail.invalid.type");
            }

            Unit unit = expenseDetail.getUnit();
            if (unit == null) {
                result.reject("expense.detail.invalid.type");
            }

            BigDecimal quantity = expenseDetail.getQuantity();
            if (quantity == null) {
                result.reject("expense.detail.invalid.type");
            }
        }

        return result;
    }

    private boolean hasDetailedTypeAttributesSet(ExpenseDetail ed) {
        return (ed.getPricePerUnit() != null || ed.getUnit() != null || ed.getQuantity() != null);
    }

}

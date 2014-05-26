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

        if (expenseDetail.getPayDate() == null) {
            result.rejectValue("payDate", "expense.detail.pay.date.null");
        }

        // Try to determine expense detail type...
        if (expenseDetail.getFullPrice() != null) {
            // Treat as 'Basic' expense type.

            if (hasDetailedTypeAttributesSet(expenseDetail)) {
                result.reject("expense.detail.invalid.type");
            }

            if (isNegativeOrZero(expenseDetail.getFullPrice())) {
                result.rejectValue("fullPrice", "expense.detail.full.price.negative.or.zero");
            }
        } else {
            // Treat as 'Detailed' expense type.

            BigDecimal pricePerUnit = expenseDetail.getPricePerUnit();
            if (pricePerUnit == null) {
                result.reject("expense.detail.invalid.type");
            } else if (isNegativeOrZero(pricePerUnit)) {
                result.rejectValue("pricePerUnit", "expense.detail.price.per.unit.negative.or.zero");
            }

            Unit unit = expenseDetail.getUnit();
            if (unit == null) {
                result.reject("expense.detail.invalid.type");
            }

            BigDecimal quantity = expenseDetail.getQuantity();
            if (quantity == null) {
                result.reject("expense.detail.invalid.type");
            } else if (isNegativeOrZero(quantity)) {
                result.rejectValue("quantity", "expense.detail.quantity.negative.or.zero");
            }
        }

        return result;
    }

    private boolean hasDetailedTypeAttributesSet(ExpenseDetail ed) {
        return (ed.getPricePerUnit() != null || ed.getUnit() != null || ed.getQuantity() != null);
    }

    private boolean isNegativeOrZero(BigDecimal number) {
        return (number.compareTo(BigDecimal.ZERO) <= 0);
    }

}

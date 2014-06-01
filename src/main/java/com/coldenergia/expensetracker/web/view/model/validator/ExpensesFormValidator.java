package com.coldenergia.expensetracker.web.view.model.validator;

import com.coldenergia.expensetracker.web.view.model.expense.ExpenseForm;
import com.coldenergia.expensetracker.web.view.model.expense.ExpensesForm;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Validates {@link ExpensesForm}. Duplicates much of the logic in the
 * {@link com.coldenergia.expensetracker.validator.ExpenseDetailValidator}
 * and {@link com.coldenergia.expensetracker.validator.ExpenseValidator}. As the
 * above-mentioned validators have their logic covered with tests, this validator
 * does not.<br>
 * User: coldenergia
 * Date: 5/31/14
 * Time: 8:02 PM
 */
@Component
public class ExpensesFormValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return ExpensesForm.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ExpensesForm expensesForm = (ExpensesForm) target;
        List<ExpenseForm> expenseForms = expensesForm.getExpenseFormList();

        if (expenseForms == null || expenseForms.isEmpty()) {
            errors.rejectValue("expenseFormList", "you.havent.specified.any.expenses");
        } else {
            for (int i = 0; i < expenseForms.size(); i++) {
                ExpenseForm expenseForm = expenseForms.get(i);
                validateExpenseForm(expenseForm, i, errors);
            }
        }

        if (expensesForm.getPayDate() == null) {
            errors.rejectValue("payDate", "you.havent.specified.pay.date");
        }
    }

    private void validateExpenseForm(ExpenseForm expenseForm, int index, Errors errors) {
        String name = expenseForm.getExpenseName();
        if (StringUtils.isEmpty(name)) {
            errors.rejectValue("expenseFormList[" + index + "].expenseName", "expense.name.empty");
        } else if (name.length() > 60) {
            errors.rejectValue("expenseFormList[" + index + "].expenseName", "expense.name.too.long");
        }

        // Try to determine expense detail type...
        if (expenseForm.getFullPrice() != null) {
            // Treat as 'Basic' expense type.

            if (hasDetailedTypeAttributesSet(expenseForm)) {
                errors.rejectValue("expenseFormList[" + index + "]", "expense.detail.invalid.type");
            }

            if (isNegativeOrZero(expenseForm.getFullPrice())) {
                errors.rejectValue("expenseFormList[" + index + "].fullPrice", "expense.detail.full.price.negative.or.zero");
            }
        } else {
            // Treat as 'Detailed' expense type.

            BigDecimal pricePerUnit = expenseForm.getPricePerUnit();
            if (pricePerUnit == null) {
                // Do nothing
            } else if (isNegativeOrZero(pricePerUnit)) {
                errors.rejectValue(
                        "expenseFormList[" + index + "].pricePerUnit",
                        "expense.detail.price.per.unit.negative.or.zero"
                );
            }

            String unit = expenseForm.getUnit();
            if (StringUtils.isEmpty(unit)) {
                // Do nothing
            } else if (unit.length() > 10) {
                errors.rejectValue("expenseFormList[" + index + "].unit", "expense.detail.unit.too.long");
            }

            BigDecimal quantity = expenseForm.getQuantity();
            if (quantity == null) {
                // Do nothing
            } else if (isNegativeOrZero(quantity)) {
                errors.rejectValue(
                        "expenseFormList[" + index + "].quantity",
                        "expense.detail.quantity.negative.or.zero"
                );
            }

            if (pricePerUnit == null || StringUtils.isEmpty(unit) || quantity == null) {
                errors.rejectValue("expenseFormList[" + index + "]", "expense.detail.invalid.type");
            }
        }
    }

    private boolean hasDetailedTypeAttributesSet(ExpenseForm form) {
        return (form.getPricePerUnit() != null || !StringUtils.isEmpty(form.getUnit()) || form.getQuantity() != null);
    }

    private boolean isNegativeOrZero(BigDecimal number) {
        return (number.compareTo(BigDecimal.ZERO) <= 0);
    }

}

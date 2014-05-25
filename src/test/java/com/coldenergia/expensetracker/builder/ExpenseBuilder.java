package com.coldenergia.expensetracker.builder;

import com.coldenergia.expensetracker.domain.Category;
import com.coldenergia.expensetracker.domain.Expense;

/**
 * User: coldenergia
 * Date: 5/3/14
 * Time: 9:15 PM
 */
public class ExpenseBuilder {

    private Expense expense;

    public ExpenseBuilder() {
        expense = new Expense();
        expense.setName("military purchases");
        expense.setCategory(new CategoryBuilder().build());
    }

    public Expense build() {
        return expense;
    }

    public ExpenseBuilder withName(String name) {
        expense.setName(name);
        return this;
    }

    public ExpenseBuilder withCategory(Category category) {
        expense.setCategory(category);
        return this;
    }

}

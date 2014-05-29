package com.coldenergia.expensetracker.domain;

/**
 * User: coldenergia
 * Date: 5/29/14
 * Time: 8:49 PM
 */
public class NamedExpenseDetailHolder {

    private ExpenseDetail expenseDetail;

    private String expenseName;

    public NamedExpenseDetailHolder() {}

    public NamedExpenseDetailHolder(ExpenseDetail expenseDetail, String expenseName) {
        this.expenseDetail = expenseDetail;
        this.expenseName = expenseName;
    }

    public ExpenseDetail getExpenseDetail() {
        return expenseDetail;
    }

    public void setExpenseDetail(ExpenseDetail expenseDetail) {
        this.expenseDetail = expenseDetail;
    }

    public String getExpenseName() {
        return expenseName;
    }

    public void setExpenseName(String expenseName) {
        this.expenseName = expenseName;
    }

}

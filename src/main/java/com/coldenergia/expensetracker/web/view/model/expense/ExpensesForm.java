package com.coldenergia.expensetracker.web.view.model.expense;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * User: coldenergia
 * Date: 5/31/14
 * Time: 1:33 PM
 */
public class ExpensesForm {

    private List<ExpenseForm> expenseFormList;

    // TODO: Change to joda time later.
    private Date payDate;

    public ExpensesForm() {
        this.expenseFormList = new ArrayList<>();
    }

    public List<ExpenseForm> getExpenseFormList() {
        return expenseFormList;
    }

    public void setExpenseFormList(List<ExpenseForm> expenseFormList) {
        this.expenseFormList = expenseFormList;
    }

    public Date getPayDate() {
        return payDate;
    }

    public void setPayDate(Date payDate) {
        this.payDate = payDate;
    }

}

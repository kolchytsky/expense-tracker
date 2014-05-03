package com.coldenergia.expensetracker.builder;

import com.coldenergia.expensetracker.domain.Expense;
import com.coldenergia.expensetracker.domain.ExpenseDetail;
import com.coldenergia.expensetracker.domain.Unit;

import java.math.BigDecimal;
import java.util.Date;

/**
 * User: coldenergia
 * Date: 5/3/14
 * Time: 9:23 PM
 */
public class ExpenseDetailBuilder {

    private ExpenseDetail expenseDetail;

    public ExpenseDetailBuilder() {
        expenseDetail = new ExpenseDetail();
        expenseDetail.setExpense(null);
        expenseDetail.setFullPrice(new BigDecimal(4));
        expenseDetail.setPayDate(new Date());
        expenseDetail.setPricePerUnit(null);
        expenseDetail.setQuantity(null);
        expenseDetail.setUnit(null);
    }

    public ExpenseDetail build() {
        return expenseDetail;
    }

    public ExpenseDetailBuilder withExpense(Expense expense) {
        expenseDetail.setExpense(expense);
        return this;
    }

    public ExpenseDetailBuilder withFullPrice(BigDecimal fullPrice) {
        expenseDetail.setFullPrice(fullPrice);
        return this;
    }

    public ExpenseDetailBuilder withPricePerUnit(BigDecimal pricePerUnit) {
        expenseDetail.setPricePerUnit(pricePerUnit);
        return this;
    }

    public ExpenseDetailBuilder withQuantity(BigDecimal quantity) {
        expenseDetail.setQuantity(quantity);
        return this;
    }

    public ExpenseDetailBuilder withUnit(Unit unit) {
        expenseDetail.setUnit(unit);
        return this;
    }

    public ExpenseDetailBuilder withPayDate(Date payDate) {
        expenseDetail.setPayDate(payDate);
        return this;
    }

}

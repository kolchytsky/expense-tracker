package com.coldenergia.expensetracker.builder;

import com.coldenergia.expensetracker.domain.Expense;
import com.coldenergia.expensetracker.domain.ExpenseDetail;

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
        expenseDetail.setExpense(new ExpenseBuilder().build());
        expenseDetail.setFullPrice(new BigDecimal(4));
        expenseDetail.setPayDate(new Date());
        expenseDetail.setPricePerUnit(null);
        expenseDetail.setQuantity(null);
        expenseDetail.setUnit(null);
    }

    public ExpenseDetail build() {
        return expenseDetail;
    }

    public ExpenseDetailBuilder withId(Long id) {
        expenseDetail.setId(id);
        return this;
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

    public ExpenseDetailBuilder withUnit(String unit) {
        expenseDetail.setUnit(unit);
        return this;
    }

    public ExpenseDetailBuilder withPayDate(Date payDate) {
        expenseDetail.setPayDate(payDate);
        return this;
    }

    /**
     * Constructs a 'Basic' expense detail.
     * */
    public ExpenseDetailBuilder basicExpense() {
        this
                .withFullPrice(BigDecimal.TEN)
                .withQuantity(null)
                .withUnit(null)
                .withPricePerUnit(null);
        return this;
    }

    /**
     * Constructs a 'Detailed' expense detail.
     * */
    public ExpenseDetailBuilder detailedExpense() {
        this
                .withQuantity(BigDecimal.ONE)
                .withUnit("lumen")
                .withPricePerUnit(BigDecimal.valueOf(4.3))
                .withFullPrice(null);
        return this;
    }

}

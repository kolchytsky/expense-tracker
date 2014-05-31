package com.coldenergia.expensetracker.web.view.model.expense;

import java.math.BigDecimal;

/**
 * User: coldenergia
 * Date: 5/31/14
 * Time: 2:49 PM
 */
public class ExpenseForm {

    private String expenseName;

    private BigDecimal quantity;

    private String unit;

    private BigDecimal pricePerUnit;

    private BigDecimal fullPrice;

    public String getExpenseName() {
        return expenseName;
    }

    public void setExpenseName(String expenseName) {
        this.expenseName = expenseName;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public BigDecimal getPricePerUnit() {
        return pricePerUnit;
    }

    public void setPricePerUnit(BigDecimal pricePerUnit) {
        this.pricePerUnit = pricePerUnit;
    }

    public BigDecimal getFullPrice() {
        return fullPrice;
    }

    public void setFullPrice(BigDecimal fullPrice) {
        this.fullPrice = fullPrice;
    }

}

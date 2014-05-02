package com.coldenergia.expensetracker.domain;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * User: coldenergia
 * Date: 5/2/14
 * Time: 7:37 PM
 */
/**
 * An expense definition ({@link Expense}) can have many {@link ExpenseDetail}s.
 * These are the actual expenses that the user records over time. There are two
 * types of expenses - 'Basic' and 'Detailed' expenses. For the basic type,
 * the user only specifies the full price paid ({@link ExpenseDetail#fullPrice}),
 * and the rest of the fields ({@link ExpenseDetail#quantity}, {@link ExpenseDetail#unit},
 * {@link ExpenseDetail#pricePerUnit}) is set to {@code null}. On the contrary, for
 * the detailed type, {@link ExpenseDetail#quantity}, {@link ExpenseDetail#unit},
 * {@link ExpenseDetail#pricePerUnit} all have meaningful values, whereas
 * {@link ExpenseDetail#fullPrice} is {@code null}.<br>
 * Another note is that the system currently supports only one currency, the so-called
 * 'credits'. A multi-currency expense-tracker would require too much input from the
 * user, which would render the system user-unfriendly.
 * */
@Entity
@Table(name = "expense_details")
public class ExpenseDetail {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "expense_id")
    private Expense expense;

    /**
     * Is populated for 'Detailed' expense type.
     * */
    private BigDecimal quantity;

    /**
     * Is populated for 'Detailed' expense type.
     * */
    @ManyToOne
    @JoinColumn(name = "unit_id")
    private Unit unit;

    /**
     * Is populated for 'Detailed' expense type.
     * */
    @Column(name = "price_per_unit")
    private BigDecimal pricePerUnit;

    /**
     * Is populated for 'Basic' expense type.
     * */
    @Column(name = "full_price")
    private BigDecimal fullPrice;

    @Temporal(TemporalType.DATE)
    @Column(name = "pay_date")
    private Date payDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Expense getExpense() {
        return expense;
    }

    public void setExpense(Expense expense) {
        this.expense = expense;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
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

    public Date getPayDate() {
        return payDate;
    }

    public void setPayDate(Date payDate) {
        this.payDate = payDate;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ExpenseDetail{");
        sb.append("id=").append(id);
        sb.append(", expense=").append(expense);
        sb.append(", quantity=").append(quantity);
        sb.append(", unit=").append(unit);
        sb.append(", pricePerUnit=").append(pricePerUnit);
        sb.append(", fullPrice=").append(fullPrice);
        sb.append(", payDate=").append(payDate);
        sb.append('}');
        return sb.toString();
    }

}

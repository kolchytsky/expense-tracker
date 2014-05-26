package com.coldenergia.expensetracker.service.expense;

import com.coldenergia.expensetracker.builder.ExpenseBuilder;
import com.coldenergia.expensetracker.builder.ExpenseDetailBuilder;
import com.coldenergia.expensetracker.builder.UnitBuilder;
import com.coldenergia.expensetracker.domain.ExpenseDetail;
import org.junit.Test;

import java.math.BigDecimal;

/**
 * {@link com.coldenergia.expensetracker.domain.ExpenseDetail}s can be of 'Basic' or
 * 'Detailed' type. Please see {@link com.coldenergia.expensetracker.domain.ExpenseDetail}.<br>
 * User: coldenergia
 * Date: 5/25/14
 * Time: 6:40 PM
 */
public class BasicAndDetailedExpenseTypesTest extends AbstractExpenseServiceTest {

    @Test
    public void shouldSaveBasicExpenseDetail() {
        // Explicitly initialize all properties inherent to 'Basic' type.
        ExpenseDetail basic = new ExpenseDetailBuilder()
                .withExpense(new ExpenseBuilder().build())
                .withFullPrice(BigDecimal.TEN)
                .withQuantity(null)
                .withUnit(null)
                .withPricePerUnit(null)
                .build();

        expenseDetailRepository.save(basic);
    }

    @Test
    public void shouldSaveDetailedExpenseDetail() {
        // Explicitly initialize all properties inherent to 'Detailed' type.
        ExpenseDetail detailed = new ExpenseDetailBuilder()
                .withExpense(new ExpenseBuilder().build())
                .withQuantity(BigDecimal.ONE)
                .withUnit(new UnitBuilder().build())
                .withPricePerUnit(BigDecimal.valueOf(4.3))
                .withFullPrice(null)
                .build();

        expenseDetailRepository.save(detailed);
    }

    @Test
    public void shouldNotSaveExpenseDetailsWithUndefinedType() {
        ExpenseDetail invalid = new ExpenseDetailBuilder().detailedExpense().withPricePerUnit(null).build();
        assertExceptionOnSave(invalid, "expense.detail.invalid.type");

        invalid = new ExpenseDetailBuilder().detailedExpense().withUnit(null).build();
        assertExceptionOnSave(invalid, "expense.detail.invalid.type");

        invalid = new ExpenseDetailBuilder().detailedExpense().withQuantity(null).build();
        assertExceptionOnSave(invalid, "expense.detail.invalid.type");

        invalid = new ExpenseDetailBuilder().detailedExpense().withFullPrice(BigDecimal.TEN).build();
        assertExceptionOnSave(invalid, "expense.detail.invalid.type");

        invalid = new ExpenseDetailBuilder().basicExpense().withPricePerUnit(BigDecimal.ONE).build();
        assertExceptionOnSave(invalid, "expense.detail.invalid.type");

        invalid = new ExpenseDetailBuilder().basicExpense().withUnit(new UnitBuilder().build()).build();
        assertExceptionOnSave(invalid, "expense.detail.invalid.type");

        invalid = new ExpenseDetailBuilder().basicExpense().withQuantity(BigDecimal.ONE).build();
        assertExceptionOnSave(invalid, "expense.detail.invalid.type");
    }

    @Test
    public void shouldNotSaveExpenseDetailWithNegativeOrZeroPrice() {
        ExpenseDetail invalid = new ExpenseDetailBuilder().detailedExpense()
                .withPricePerUnit(BigDecimal.valueOf(-1)).build();
        assertExceptionOnSave(invalid, "expense.detail.price.per.unit.negative.or.zero");

        invalid = new ExpenseDetailBuilder().detailedExpense()
                .withPricePerUnit(BigDecimal.ZERO).build();
        assertExceptionOnSave(invalid, "expense.detail.price.per.unit.negative.or.zero");

        invalid = new ExpenseDetailBuilder().basicExpense()
                .withFullPrice(BigDecimal.valueOf(-1)).build();
        assertExceptionOnSave(invalid, "expense.detail.full.price.negative.or.zero");

        invalid = new ExpenseDetailBuilder().basicExpense()
                .withFullPrice(BigDecimal.ZERO).build();
        assertExceptionOnSave(invalid, "expense.detail.full.price.negative.or.zero");
    }

    @Test
    public void shouldNotSaveDetailedExpenseDetailWithNegativeOrZeroQuantity() {
        ExpenseDetail invalid = new ExpenseDetailBuilder().detailedExpense()
                .withQuantity(BigDecimal.valueOf(-1)).build();
        assertExceptionOnSave(invalid, "expense.detail.quantity.negative.or.zero");

        invalid = new ExpenseDetailBuilder().detailedExpense()
                .withQuantity(BigDecimal.ZERO).build();
        assertExceptionOnSave(invalid, "expense.detail.quantity.negative.or.zero");
    }

}

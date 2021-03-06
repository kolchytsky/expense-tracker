package com.coldenergia.expensetracker.service.expense;

import com.coldenergia.expensetracker.builder.ExpenseBuilder;
import com.coldenergia.expensetracker.builder.ExpenseDetailBuilder;
import com.coldenergia.expensetracker.domain.Expense;
import com.coldenergia.expensetracker.domain.ExpenseDetail;
import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.springframework.data.domain.Pageable;

import static org.mockito.Mockito.*;

/**
 * User: coldenergia
 * Date: 5/25/14
 * Time: 4:58 PM
 */
public class ExpenseServiceTest extends AbstractExpenseServiceTest {

    @Before
    public void setup() {
        super.setup();
    }

    @Test
    public void shouldSaveExpense() {
        Expense expense = new ExpenseBuilder().build();
        expenseService.save(expense);
        verify(expenseRepository).save(expense);
    }

    @Test
    public void shouldNotSaveExpenseWithEmptyName() {
        Expense invalid = new ExpenseBuilder().withName("").build();
        assertExceptionOnSave(invalid, "expense.name.empty");

        invalid = new ExpenseBuilder().withName(null).build();
        assertExceptionOnSave(invalid, "expense.name.empty");
    }

    @Test
    public void shouldNotSaveExpenseWithNameExceedingSixtyChars() {
        String invalidName = StringUtils.repeat("a", 61);
        Expense invalid = new ExpenseBuilder().withName(invalidName).build();
        assertExceptionOnSave(invalid, "expense.name.too.long");
    }

    @Test
    public void shouldSaveExpenseWithNameNotExceedingSixtyChars() {
        String validName = StringUtils.repeat("a", 60);
        Expense valid = new ExpenseBuilder().withName(validName).build();
        expenseService.save(valid);
    }

    @Test
    public void shouldNotSaveExpenseWhichDoesNotBelongToCategory() {
        Expense expense = new ExpenseBuilder().withCategory(null).build();
        assertExceptionOnSave(expense, "expense.category.null");
    }

    @Test
    public void shouldSaveExpenseDetail() {
        ExpenseDetail expenseDetail = new ExpenseDetailBuilder().build();
        expenseService.save(expenseDetail);
        verify(expenseDetailRepository).save(expenseDetail);
    }

    @Test
    public void shouldNotSaveExpenseDetailWhichDoesNotBelongToExpense() {
        ExpenseDetail expenseDetail = new ExpenseDetailBuilder().withExpense(null).build();
        assertExceptionOnSave(expenseDetail, "expense.detail.expense.null");
    }

    @Test
    public void shouldNotSaveExpenseDetailWithoutPayDate() {
        ExpenseDetail expenseDetail = new ExpenseDetailBuilder().withPayDate(null).build();
        assertExceptionOnSave(expenseDetail, "expense.detail.pay.date.null");
    }

    @Test
    public void shouldNotSaveDetailedExpenseWithEmptyUnit() {
        ExpenseDetail invalid = new ExpenseDetailBuilder().detailedExpense().withUnit("").build();
        assertExceptionOnSave(invalid, "expense.detail.unit.empty");
    }

    @Test
    public void shouldNotSaveDetailedExpenseWithUnitExceedingTenChars() {
        String invalidUnit = StringUtils.repeat("a", 11);
        ExpenseDetail invalid = new ExpenseDetailBuilder().detailedExpense().withUnit(invalidUnit).build();
        assertExceptionOnSave(invalid, "expense.detail.unit.too.long");
    }

    @Test
    public void shouldSaveDetailedExpenseWithUnitNotExceedingTenChars() {
        String validUnit = StringUtils.repeat("a", 10);
        ExpenseDetail valid = new ExpenseDetailBuilder().detailedExpense().withUnit(validUnit).build();
        expenseService.save(valid);
    }

    @Test
    public void shouldFindExpensesByDomainId() {
        Pageable pageable = mock(Pageable.class);
        when(pageable.getPageSize()).thenReturn(20);
        when(pageable.getPageNumber()).thenReturn(1);
        expenseService.findExpensesByDomainId(4L, pageable);
        verify(expenseDetailRepository).findByExpenseCategoryDomainId(4L, pageable);
    }

}

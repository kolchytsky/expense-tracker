package com.coldenergia.expensetracker.service.expense;

import com.coldenergia.expensetracker.builder.ExpenseDetailBuilder;
import com.coldenergia.expensetracker.domain.ExpenseDetail;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.Assert.assertEquals;

/**
 * User: coldenergia
 * Date: 5/26/14
 * Time: 8:39 PM
 */
public class LogExpenseTest extends AbstractExpenseServiceTest {

    @Before
    public void setup() {
        super.setup();
    }

    @Test
    public void shouldCreateExpenseDetail() {
        ExpenseDetail expenseDetail = new ExpenseDetailBuilder().build();
        String name = "flak cannon";
        expenseService.logExpense(expenseDetail, name, 1L);
        verify(expenseDetailRepository).save(any(ExpenseDetail.class));
    }

    @Test
    public void shouldCreateNewExpenseIfThereIsNoExpenseWithThisNameInDomain() {
        ExpenseDetail expenseDetail = new ExpenseDetailBuilder().build();
        String name = "flak cannon";
        // TODO: Domain id will be retrieved liek /domains/4/expenses/new
    }

}

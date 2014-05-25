package com.coldenergia.expensetracker.service.expense;

import com.coldenergia.expensetracker.builder.ExpenseBuilder;
import com.coldenergia.expensetracker.builder.ExpenseDetailBuilder;
import com.coldenergia.expensetracker.domain.Expense;
import com.coldenergia.expensetracker.domain.ExpenseDetail;
import com.coldenergia.expensetracker.repository.ExpenseDetailRepository;
import com.coldenergia.expensetracker.repository.ExpenseRepository;
import com.coldenergia.expensetracker.service.ExpenseService;
import com.coldenergia.expensetracker.service.ServiceIntegrationTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.coldenergia.expensetracker.internal.test.data.TestDataInitializer.*;
import static org.junit.Assert.*;

/**
 * User: coldenergia
 * Date: 5/25/14
 * Time: 5:26 PM
 */
public class ExpenseServiceIntegrationTest extends ServiceIntegrationTest {

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private ExpenseDetailRepository expenseDetailRepository;

    @Autowired
    private ExpenseService expenseService;

    @Test
    public void shouldSaveExpense() {
        Expense expense = new ExpenseBuilder().withCategory(categories(MILITARY_RESEARCH)).build();
        assertNull(expense.getId());
        long initialCount = expenseRepository.count();
        expenseService.save(expense);
        long finalCount = expenseRepository.count();
        assertEquals(1L, finalCount - initialCount);
        assertNotNull(expense.getId());
    }

    @Test
    public void shouldSaveExpenseDetail() {
        ExpenseDetail expenseDetail = new ExpenseDetailBuilder().withExpense(expenses(SHOCK_RIFLE)).build();
        assertNull(expenseDetail.getId());
        long initialCount = expenseDetailRepository.count();
        expenseService.save(expenseDetail);
        long finalCount = expenseDetailRepository.count();
        assertEquals(1L, finalCount - initialCount);
        assertNotNull(expenseDetail.getId());
    }

}

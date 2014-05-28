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

    @Test
    public void shouldLogExpenseWithExistingName() {
        ExpenseDetail expenseDetail = new ExpenseDetailBuilder().basicExpense().build();
        String expenseName = expenses(SHOCK_RIFLE).getName();
        long initialExpenseCount = expenseRepository.count();
        long initialExpenseDetailCount = expenseDetailRepository.count();

        ExpenseDetail detail = expenseService.logExpense(expenseDetail, expenseName, domains(ACATANA).getId());

        long finalExpenseCount = expenseRepository.count();
        long finalExpenseDetailCount = expenseDetailRepository.count();

        assertEquals("Shouldn't have created a new expense",
                0L, finalExpenseCount - initialExpenseCount);
        assertEquals("Should've created a new expense detail",
                1L, finalExpenseDetailCount - initialExpenseDetailCount);

        assertNotNull(detail);
        assertNotNull(detail.getId());
        assertEquals(expenses(SHOCK_RIFLE).getId(), detail.getExpense().getId());
    }

    @Test
    public void shouldLogExpenseWithNonExistingName() {
        ExpenseDetail expenseDetail = new ExpenseDetailBuilder().detailedExpense().build();
        String expenseName = "Flak Cannon";
        long initialExpenseCount = expenseRepository.count();
        long initialExpenseDetailCount = expenseDetailRepository.count();

        ExpenseDetail detail = expenseService.logExpense(expenseDetail, expenseName, domains(ACATANA).getId());

        long finalExpenseCount = expenseRepository.count();
        long finalExpenseDetailCount = expenseDetailRepository.count();

        assertEquals("Should've created a new expense",
                1L, finalExpenseCount - initialExpenseCount);
        assertEquals("Should've created a new expense detail",
                1L, finalExpenseDetailCount - initialExpenseDetailCount);

        assertNotNull(detail);
        assertNotNull(detail.getId());
        assertEquals(expenseName, detail.getExpense().getName());
    }

}

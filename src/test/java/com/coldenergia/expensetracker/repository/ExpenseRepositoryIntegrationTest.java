package com.coldenergia.expensetracker.repository;

import com.coldenergia.expensetracker.builder.ExpenseBuilder;
import com.coldenergia.expensetracker.domain.Expense;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

/**
 * User: coldenergia
 * Date: 5/3/14
 * Time: 9:13 PM
 */
public class ExpenseRepositoryIntegrationTest extends RepositoryIntegrationTest {

    @Autowired
    private ExpenseRepository expenseRepository;

    @Test
    public void shouldSaveExpense() {
        Expense expense = new ExpenseBuilder().build();
        Expense retrievedExpense = expenseRepository.save(expense);
        assertNotNull(retrievedExpense);
        assertEquals(expense, retrievedExpense);
    }

}

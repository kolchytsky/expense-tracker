package com.coldenergia.expensetracker.repository;

import com.coldenergia.expensetracker.builder.ExpenseDetailBuilder;
import com.coldenergia.expensetracker.domain.ExpenseDetail;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

/**
 * User: coldenergia
 * Date: 5/3/14
 * Time: 9:22 PM
 */
public class ExpenseDetailRepositoryIntegrationTest extends RepositoryIntegrationTest {

    @Autowired
    private ExpenseDetailRepository expenseDetailRepository;

    @Test
    public void shouldSaveExpenseDetail() {
        ExpenseDetail expenseDetail = new ExpenseDetailBuilder().build();
        ExpenseDetail retrievedExpenseDetail = expenseDetailRepository.save(expenseDetail);
        assertNotNull(retrievedExpenseDetail);
        assertEquals(expenseDetail, retrievedExpenseDetail);
    }

}

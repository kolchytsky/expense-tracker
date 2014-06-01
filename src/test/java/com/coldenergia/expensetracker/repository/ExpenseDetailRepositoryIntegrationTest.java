package com.coldenergia.expensetracker.repository;

import com.coldenergia.expensetracker.builder.ExpenseDetailBuilder;
import com.coldenergia.expensetracker.domain.Domain;
import com.coldenergia.expensetracker.domain.ExpenseDetail;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import static com.coldenergia.expensetracker.internal.test.data.TestDataInitializer.SHOCK_RIFLE;
import static com.coldenergia.expensetracker.internal.test.data.TestDataInitializer.expenses;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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
        ExpenseDetail expenseDetail = new ExpenseDetailBuilder().withExpense(expenses(SHOCK_RIFLE)).build();
        ExpenseDetail retrievedExpenseDetail = expenseDetailRepository.save(expenseDetail);
        assertNotNull(retrievedExpenseDetail);
        assertEquals(expenseDetail, retrievedExpenseDetail);
    }

    @Test
    public void shouldFindAllWithPageableSupport() {
        for (int i = 0; i < 41; i++) {
            ExpenseDetail expenseDetail = new ExpenseDetailBuilder().withExpense(expenses(SHOCK_RIFLE)).build();
            expenseDetailRepository.save(expenseDetail);
        }
        Pageable pageable = mock(Pageable.class);
        when(pageable.getPageSize()).thenReturn(20);
        when(pageable.getPageNumber()).thenReturn(1);
        Page<ExpenseDetail> page = expenseDetailRepository.findAll(pageable);
        assertTrue(page.hasContent());
        assertEquals(pageable.getPageSize(), page.getSize());
    }

    @Test
    public void shouldFindByExpenseCategoryDomainWithPageableSupport() {
        for (int i = 0; i < 41; i++) {
            ExpenseDetail expenseDetail = new ExpenseDetailBuilder().withExpense(expenses(SHOCK_RIFLE)).build();
            expenseDetailRepository.save(expenseDetail);
        }
        Pageable pageable = mock(Pageable.class);
        when(pageable.getPageSize()).thenReturn(20);
        when(pageable.getPageNumber()).thenReturn(1);
        Domain domain = expenses(SHOCK_RIFLE).getCategory().getDomain();
        Page<ExpenseDetail> page = expenseDetailRepository.findByExpenseCategoryDomainId(domain.getId(), pageable);
        assertTrue(page.hasContent());
        assertEquals(pageable.getPageSize(), page.getSize());
    }

}

package com.coldenergia.expensetracker.repository.expense;

import com.coldenergia.expensetracker.builder.ExpenseDetailBuilder;
import com.coldenergia.expensetracker.domain.Domain;
import com.coldenergia.expensetracker.domain.ExpenseDetail;
import com.coldenergia.expensetracker.repository.ExpenseDetailRepository;
import com.coldenergia.expensetracker.repository.RepositoryIntegrationTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;

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

    @Test
    public void shouldDeleteByIdList() {
        long initialCount = expenseDetailRepository.count();

        ExpenseDetail detail1 = new ExpenseDetailBuilder().withExpense(expenses(SHOCK_RIFLE)).build();
        ExpenseDetail detail2 = new ExpenseDetailBuilder().withExpense(expenses(SHOCK_RIFLE)).build();

        expenseDetailRepository.save(detail1);
        expenseDetailRepository.save(detail2);
        long afterSaveCount = expenseDetailRepository.count();
        assertEquals(2L, afterSaveCount - initialCount);

        List<Long> idList = Arrays.asList(detail1.getId(), detail2.getId());

        expenseDetailRepository.delete(idList);

        long afterDeletionCount = expenseDetailRepository.count();
        assertEquals(initialCount, afterDeletionCount);
    }

}

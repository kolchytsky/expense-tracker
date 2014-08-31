package com.coldenergia.expensetracker.service.expense;

import com.coldenergia.expensetracker.builder.ExpenseBuilder;
import com.coldenergia.expensetracker.domain.Expense;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * User: coldenergia
 * Date: 8/31/14
 * Time: 3:58 PM
 */
public class DeleteExpensesByDetailsIdListTest extends AbstractExpenseServiceTest {

    private Expense redeemer;

    private Expense spiderMine;

    @Before
    public void setup() {
        super.setup();
        redeemer = new ExpenseBuilder().withName("Redeemer").build();
        spiderMine = new ExpenseBuilder().withName("Spider Mine").build();
    }

    @Test
    public void shouldDeleteExpensesByChildDetailsIdList() {
        List<Long> idList = Arrays.asList(4L, 6L);

        List<Expense> expenseList = Arrays.asList(redeemer, spiderMine);
        when(expenseRepository.findExpensesByChildDetailsIdList(idList)).thenReturn(expenseList);
        when(expenseRepository.getExpenseDetailCountForExpense(redeemer)).thenReturn(3L);
        when(expenseRepository.getExpenseDetailCountForExpense(spiderMine)).thenReturn(0L);

        expenseService.deleteExpensesByChildDetailsIdList(idList);

        verify(expenseRepository).findExpensesByChildDetailsIdList(idList);
        verify(expenseRepository).getExpenseDetailCountForExpense(redeemer);
        verify(expenseRepository).getExpenseDetailCountForExpense(spiderMine);

        verify(expenseDetailRepository).delete(idList);
        verify(expenseRepository, never()).delete(redeemer);
        verify(expenseRepository).delete(spiderMine);
    }

    // TODO: Add more tests here.

}

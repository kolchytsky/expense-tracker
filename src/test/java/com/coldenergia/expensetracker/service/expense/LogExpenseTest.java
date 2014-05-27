package com.coldenergia.expensetracker.service.expense;

import com.coldenergia.expensetracker.builder.CategoryBuilder;
import com.coldenergia.expensetracker.builder.DomainBuilder;
import com.coldenergia.expensetracker.builder.ExpenseBuilder;
import com.coldenergia.expensetracker.builder.ExpenseDetailBuilder;
import com.coldenergia.expensetracker.domain.Category;
import com.coldenergia.expensetracker.domain.Domain;
import com.coldenergia.expensetracker.domain.Expense;
import com.coldenergia.expensetracker.domain.ExpenseDetail;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

/**
 * User: coldenergia
 * Date: 5/26/14
 * Time: 8:39 PM
 */
public class LogExpenseTest extends AbstractExpenseServiceTest {

    private Domain domain;

    private Category rootCategory;

    @Before
    public void setup() {
        super.setup();
        domain = new DomainBuilder().withId(4L).build();
        rootCategory = new CategoryBuilder().withDomain(domain).build();
        when(categoryRepository.getDomainRootCategory(domain.getId())).thenReturn(rootCategory);
    }

    @Test
    public void shouldCreateExpenseDetail() {
        ExpenseDetail expenseDetail = new ExpenseDetailBuilder().build();
        String name = "flak cannon";
        expenseService.logExpense(expenseDetail, name, domain.getId());
        verify(expenseDetailRepository).save(any(ExpenseDetail.class));
    }

    @Test
    public void shouldCreateNewExpenseIfThereIsNoExpenseWithThisNameInDomain() {
        ExpenseDetail expenseDetail = new ExpenseDetailBuilder().build();
        String name = "flak cannon";
        when(expenseRepository.findByNameAndDomainId(name, domain.getId())).thenReturn(null);

        expenseService.logExpense(expenseDetail, name, domain.getId());

        ArgumentCaptor<Expense> expenseArgumentCaptor = ArgumentCaptor.forClass(Expense.class);
        verify(expenseRepository).save(expenseArgumentCaptor.capture());
        Expense expense = expenseArgumentCaptor.getValue();
        assertEquals(name, expense.getName());

        verify(expenseDetailRepository).save(any(ExpenseDetail.class));
    }

    @Test
    public void shouldAssignNewExpenseToRootCategoryIfCreatingNewExpense() {
        String name = "flak cannon";
        when(expenseRepository.findByNameAndDomainId(name, domain.getId())).thenReturn(null);

        expenseService.logExpense(new ExpenseDetailBuilder().build(), name, domain.getId());

        ArgumentCaptor<Expense> expenseArgumentCaptor = ArgumentCaptor.forClass(Expense.class);
        verify(expenseRepository).save(expenseArgumentCaptor.capture());
        Expense expense = expenseArgumentCaptor.getValue();
        assertEquals(rootCategory, expense.getCategory());
    }

    @Test
    public void shouldNotCreateNewExpenseIfThereIsExpenseWithSuchNameInDomain() {
        String name = "redeemer";
        Expense redeemerExpense = new ExpenseBuilder().withName(name).build();
        when(expenseRepository.findByNameAndDomainId(name, domain.getId())).thenReturn(redeemerExpense);

        expenseService.logExpense(new ExpenseDetailBuilder().build(), name, domain.getId());

        verify(expenseRepository, never()).save(any(Expense.class));

        ArgumentCaptor<ExpenseDetail> expenseDetailArgumentCaptor = ArgumentCaptor.forClass(ExpenseDetail.class);
        verify(expenseDetailRepository).save(expenseDetailArgumentCaptor.capture());
        ExpenseDetail expenseDetail = expenseDetailArgumentCaptor.getValue();
        assertEquals(redeemerExpense, expenseDetail.getExpense());
    }

}

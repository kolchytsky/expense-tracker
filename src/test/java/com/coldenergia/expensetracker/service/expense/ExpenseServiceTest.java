package com.coldenergia.expensetracker.service.expense;

import com.coldenergia.expensetracker.builder.ExpenseBuilder;
import com.coldenergia.expensetracker.domain.Expense;
import com.coldenergia.expensetracker.repository.ExpenseDetailRepository;
import com.coldenergia.expensetracker.repository.ExpenseRepository;
import com.coldenergia.expensetracker.service.ExpenseService;
import com.coldenergia.expensetracker.service.ExpenseServiceImpl;
import com.coldenergia.expensetracker.service.ServiceException;
import com.coldenergia.expensetracker.validator.ExpenseValidator;
import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * User: coldenergia
 * Date: 5/25/14
 * Time: 4:58 PM
 */
public class ExpenseServiceTest {

    private ExpenseRepository expenseRepository;

    private ExpenseDetailRepository expenseDetailRepository;

    private ExpenseService expenseService;

    @Before
    public void setup() {
        expenseRepository = mock(ExpenseRepository.class);
        expenseDetailRepository = mock(ExpenseDetailRepository.class);
        expenseService = new ExpenseServiceImpl(
                expenseRepository,
                expenseDetailRepository,
                new ExpenseValidator()
        );
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

    private void assertExceptionOnSave(Expense invalid, String errorCode) {
        try {
            expenseService.save(invalid);
            fail("Should've thrown an exception here");
        } catch (ServiceException expected) {
            assertNotNull(expected.getMessage());
            assertTrue(expected.getMessage().contains(errorCode));
        }
    }

}

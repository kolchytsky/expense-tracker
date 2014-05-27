package com.coldenergia.expensetracker.service.expense;

import com.coldenergia.expensetracker.domain.Expense;
import com.coldenergia.expensetracker.domain.ExpenseDetail;
import com.coldenergia.expensetracker.repository.CategoryRepository;
import com.coldenergia.expensetracker.repository.ExpenseDetailRepository;
import com.coldenergia.expensetracker.repository.ExpenseRepository;
import com.coldenergia.expensetracker.service.ExpenseService;
import com.coldenergia.expensetracker.service.ExpenseServiceImpl;
import com.coldenergia.expensetracker.service.ServiceException;
import com.coldenergia.expensetracker.validator.ExpenseDetailValidator;
import com.coldenergia.expensetracker.validator.ExpenseValidator;
import org.junit.Before;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;

/**
 * User: coldenergia
 * Date: 5/25/14
 * Time: 6:36 PM
 */
public class AbstractExpenseServiceTest {

    protected ExpenseRepository expenseRepository;

    protected ExpenseDetailRepository expenseDetailRepository;

    protected CategoryRepository categoryRepository;

    protected ExpenseService expenseService;

    @Before
    public void setup() {
        expenseRepository = mock(ExpenseRepository.class);
        expenseDetailRepository = mock(ExpenseDetailRepository.class);
        categoryRepository = mock(CategoryRepository.class);
        expenseService = new ExpenseServiceImpl(
                expenseRepository,
                expenseDetailRepository,
                categoryRepository,
                new ExpenseValidator(),
                new ExpenseDetailValidator()
        );
    }

    // TODO: When are you going to write a custom assertion for that, pal ?
    protected void assertExceptionOnSave(Expense invalid, String errorCode) {
        try {
            expenseService.save(invalid);
            fail("Should've thrown an exception here");
        } catch (ServiceException expected) {
            assertNotNull(expected.getMessage());
            assertTrue(expected.getMessage().contains(errorCode));
        }
    }

    protected void assertExceptionOnSave(ExpenseDetail invalid, String errorCode) {
        try {
            expenseService.save(invalid);
            fail("Should've thrown an exception here");
        } catch (ServiceException expected) {
            assertNotNull(expected.getMessage());
            assertTrue(expected.getMessage().contains(errorCode));
        }
    }

}

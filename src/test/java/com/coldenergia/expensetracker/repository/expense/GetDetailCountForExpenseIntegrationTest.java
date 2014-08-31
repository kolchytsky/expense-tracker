package com.coldenergia.expensetracker.repository.expense;

import com.coldenergia.expensetracker.builder.ExpenseBuilder;
import com.coldenergia.expensetracker.builder.ExpenseDetailBuilder;
import com.coldenergia.expensetracker.domain.Expense;
import com.coldenergia.expensetracker.domain.ExpenseDetail;
import com.coldenergia.expensetracker.repository.ExpenseDetailRepository;
import com.coldenergia.expensetracker.repository.ExpenseRepository;
import com.coldenergia.expensetracker.repository.RepositoryIntegrationTest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

import static com.coldenergia.expensetracker.internal.test.data.TestDataInitializer.MILITARY_RESEARCH;
import static com.coldenergia.expensetracker.internal.test.data.TestDataInitializer.categories;

/**
 * User: coldenergia
 * Date: 8/31/14
 * Time: 3:38 PM
 */
public class GetDetailCountForExpenseIntegrationTest extends RepositoryIntegrationTest {

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private ExpenseDetailRepository expenseDetailRepository;

    private Expense redeemer;

    @Before
    public void setup() {
        redeemer = new ExpenseBuilder().withName("Redeemer").withCategory(categories(MILITARY_RESEARCH)).build();
        List<ExpenseDetail> details = new ArrayList<>();
        details.add(new ExpenseDetailBuilder().withExpense(redeemer).build());
        details.add(new ExpenseDetailBuilder().detailedExpense().withExpense(redeemer).build());
        details.add(new ExpenseDetailBuilder().basicExpense().withExpense(redeemer).build());

        expenseRepository.save(redeemer);
        expenseDetailRepository.save(details);
    }

    @Test
    public void shouldGetExpenseDetailCountForExpense() {
        long count = expenseRepository.getExpenseDetailCountForExpense(redeemer);
        assertEquals(3, count);
    }

}

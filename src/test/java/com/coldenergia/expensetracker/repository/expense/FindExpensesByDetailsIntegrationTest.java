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

import java.util.Arrays;
import java.util.List;

import static com.coldenergia.expensetracker.internal.test.data.TestDataInitializer.MILITARY_RESEARCH;
import static com.coldenergia.expensetracker.internal.test.data.TestDataInitializer.categories;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * User: coldenergia
 * Date: 8/31/14
 * Time: 2:43 PM
 */
public class FindExpensesByDetailsIntegrationTest extends RepositoryIntegrationTest {

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private ExpenseDetailRepository expenseDetailRepository;

    private Expense redeemer;

    private ExpenseDetail redeemerDetail1;

    private ExpenseDetail redeemerDetail2;

    private Expense spiderMine;

    private ExpenseDetail spiderMineDetail;

    @Before
    public void setup() {
        redeemer = new ExpenseBuilder().withName("Redeemer").withCategory(categories(MILITARY_RESEARCH)).build();
        redeemerDetail1 = new ExpenseDetailBuilder().withExpense(redeemer).build();
        redeemerDetail2 = new ExpenseDetailBuilder().withExpense(redeemer).build();
        spiderMine = new ExpenseBuilder().withName("Spider mine").withCategory(categories(MILITARY_RESEARCH)).build();
        spiderMineDetail = new ExpenseDetailBuilder().withExpense(spiderMine).build();

        expenseRepository.save(redeemer);
        expenseRepository.save(spiderMine);
        expenseDetailRepository.save(redeemerDetail1);
        expenseDetailRepository.save(redeemerDetail2);
        expenseDetailRepository.save(spiderMineDetail);
    }

    @Test
    public void shouldFindExpensesByChildDetailsIdList() {
        List<Long> idList = Arrays.asList(redeemerDetail1.getId(), redeemerDetail2.getId(), spiderMineDetail.getId());

        List<Expense> expenses = expenseRepository.findExpensesByChildDetailsIdList(idList);

        assertEquals(2, expenses.size());

        Long firstExpenseId = expenses.get(0).getId();
        Long secondExpenseId = expenses.get(1).getId();
        assertTrue(firstExpenseId == redeemer.getId() || firstExpenseId == spiderMine.getId());
        assertTrue(secondExpenseId == redeemer.getId() || secondExpenseId == spiderMine.getId());
    }

}

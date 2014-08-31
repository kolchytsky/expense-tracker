package com.coldenergia.expensetracker.service.expense;

import com.coldenergia.expensetracker.builder.ExpenseBuilder;
import com.coldenergia.expensetracker.builder.ExpenseDetailBuilder;
import com.coldenergia.expensetracker.domain.Expense;
import com.coldenergia.expensetracker.domain.ExpenseDetail;
import com.coldenergia.expensetracker.repository.ExpenseDetailRepository;
import com.coldenergia.expensetracker.repository.ExpenseRepository;
import com.coldenergia.expensetracker.service.ExpenseService;
import com.coldenergia.expensetracker.service.ServiceIntegrationTest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;

import static com.coldenergia.expensetracker.internal.test.data.TestDataInitializer.MILITARY_RESEARCH;
import static com.coldenergia.expensetracker.internal.test.data.TestDataInitializer.categories;

import static org.junit.Assert.assertEquals;

/**
 * User: coldenergia
 * Date: 8/31/14
 * Time: 4:36 PM
 */
public class DeleteExpensesByDetailsIdListIntegrationTest extends ServiceIntegrationTest {

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private ExpenseDetailRepository expenseDetailRepository;

    @Autowired
    private ExpenseService expenseService;

    private Expense redeemer;

    private Expense spiderMine;

    private Expense minigun;

    private Expense lightningGun;

    private ExpenseDetail redeemerDetail1;

    private ExpenseDetail redeemerDetail2;

    private ExpenseDetail spiderMineDetail;

    private ExpenseDetail minigunDetail;

    private ExpenseDetail lightningGunDetail1;

    private ExpenseDetail lightningGunDetail2;

    @Before
    public void setup() {
        redeemer = saveExpenseWithName("Redeemer");
        spiderMine = saveExpenseWithName("Spider mine");
        minigun = saveExpenseWithName("Minigun");
        lightningGun = saveExpenseWithName("Lightning gun");

        redeemerDetail1 = createAndSaveNewExpenseDetailForExpense(redeemer);
        redeemerDetail2 = createAndSaveNewExpenseDetailForExpense(redeemer);
        spiderMineDetail = createAndSaveNewExpenseDetailForExpense(spiderMine);
        minigunDetail = createAndSaveNewExpenseDetailForExpense(minigun);
        lightningGunDetail1 = createAndSaveNewExpenseDetailForExpense(lightningGun);
        lightningGunDetail2 = createAndSaveNewExpenseDetailForExpense(lightningGun);
    }

    @Test
    public void shouldDeleteBothExpensesAndExpenseDetails() {
        long initialDetailCount = expenseDetailRepository.count();
        long initialExpenseCount = expenseRepository.count();

        List<Long> idList = Arrays.asList(redeemerDetail1.getId(), redeemerDetail2.getId(), spiderMineDetail.getId());

        expenseService.deleteExpensesByChildDetailsIdList(idList);

        long finalDetailCount = expenseDetailRepository.count();
        long finalExpenseCount = expenseRepository.count();

        assertEquals(3L, initialDetailCount - finalDetailCount);
        assertEquals(2L, initialExpenseCount - finalExpenseCount);
    }

    @Test
    public void shouldNotDeleteExpenseIfThereAreOtherDetailsLeftButOnlyDeleteDetails() {
        long initialDetailCount = expenseDetailRepository.count();
        long initialExpenseCount = expenseRepository.count();
        long initialLightningGunDetailCount = expenseRepository.getExpenseDetailCountForExpense(lightningGun);

        List<Long> idList = Arrays.asList(lightningGunDetail1.getId(), minigunDetail.getId());

        expenseService.deleteExpensesByChildDetailsIdList(idList);

        long finalDetailCount = expenseDetailRepository.count();
        long finalExpenseCount = expenseRepository.count();
        long finalLightningGunDetailCount = expenseRepository.getExpenseDetailCountForExpense(lightningGun);

        assertEquals(2L, initialDetailCount - finalDetailCount);
        assertEquals("Should've deleted only one of two associated with the expense details expenses",
                1L, initialExpenseCount - finalExpenseCount);
        assertEquals("Should've deleted one of the two expense details associated with this expense.",
                1L, initialLightningGunDetailCount - finalLightningGunDetailCount);
    }

    private Expense saveExpenseWithName(String name) {
        Expense expense = new ExpenseBuilder().withName(name).withCategory(categories(MILITARY_RESEARCH)).build();
        expenseRepository.save(expense);
        return expense;
    }

    private ExpenseDetail createAndSaveNewExpenseDetailForExpense(Expense expense) {
        ExpenseDetail expenseDetail = new ExpenseDetailBuilder().withExpense(expense).build();
        expenseDetailRepository.save(expenseDetail);
        return expenseDetail;
    }

}

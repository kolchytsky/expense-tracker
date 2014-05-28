package com.coldenergia.expensetracker.domain;

import org.junit.Test;

import static com.coldenergia.expensetracker.domain.JpaAssertions.assertTableExists;
import static com.coldenergia.expensetracker.domain.JpaAssertions.assertTableHasColumn;

/**
 * User: coldenergia
 * Date: 5/2/14
 * Time: 7:35 PM
 */
public class ExpenseDetailMappingIntegrationTest extends MappingIntegrationTest {

    public static final String EXPENSE_DETAILS_TABLE_NAME = "expense_details";

    @Test
    public void shouldCreateExpenseDetailsTable() {
        assertTableExists(getEntityManager(), EXPENSE_DETAILS_TABLE_NAME);
    }

    @Test
    public void shouldCreateColumnsForExpenseDetailsTable() {
        assertTableHasColumn(getEntityManager(), EXPENSE_DETAILS_TABLE_NAME, "id");
        assertTableHasColumn(getEntityManager(), EXPENSE_DETAILS_TABLE_NAME, "expense_id");
        assertTableHasColumn(getEntityManager(), EXPENSE_DETAILS_TABLE_NAME, "quantity");
        assertTableHasColumn(getEntityManager(), EXPENSE_DETAILS_TABLE_NAME, "unit");
        assertTableHasColumn(getEntityManager(), EXPENSE_DETAILS_TABLE_NAME, "price_per_unit");
        assertTableHasColumn(getEntityManager(), EXPENSE_DETAILS_TABLE_NAME, "full_price");
        assertTableHasColumn(getEntityManager(), EXPENSE_DETAILS_TABLE_NAME, "pay_date");
    }

}

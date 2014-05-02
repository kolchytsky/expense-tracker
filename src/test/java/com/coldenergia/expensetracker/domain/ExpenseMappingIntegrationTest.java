package com.coldenergia.expensetracker.domain;

import org.junit.Test;

import static com.coldenergia.expensetracker.domain.JpaAssertions.assertTableExists;
import static com.coldenergia.expensetracker.domain.JpaAssertions.assertTableHasColumn;

/**
 * User: coldenergia
 * Date: 5/2/14
 * Time: 6:47 PM
 */
public class ExpenseMappingIntegrationTest extends MappingIntegrationTest {

    @Test
    public void shouldCreateExpensesTable() {
        assertTableExists(getEntityManager(), "expenses");
    }

    @Test
    public void shouldCreateColumnsForExpensesTable() {
        assertTableHasColumn(getEntityManager(), "expenses", "id");
        assertTableHasColumn(getEntityManager(), "expenses", "name");
        assertTableHasColumn(getEntityManager(), "expenses", "category_id");
    }

}

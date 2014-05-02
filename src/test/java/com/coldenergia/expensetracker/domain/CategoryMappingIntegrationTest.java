package com.coldenergia.expensetracker.domain;

import org.junit.Test;

import static com.coldenergia.expensetracker.domain.JpaAssertions.assertTableExists;
import static com.coldenergia.expensetracker.domain.JpaAssertions.assertTableHasColumn;

/**
 * User: coldenergia
 * Date: 5/2/14
 * Time: 6:28 PM
 */
public class CategoryMappingIntegrationTest extends MappingIntegrationTest {

    @Test
    public void shouldCreateCategoriesTable() {
        assertTableExists(getEntityManager(), "categories");
    }

    @Test
    public void shouldCreateColumnsForCategoriesTable() {
        assertTableHasColumn(getEntityManager(), "categories", "id");
        assertTableHasColumn(getEntityManager(), "categories", "name");
        assertTableHasColumn(getEntityManager(), "categories", "parent_id");
        assertTableHasColumn(getEntityManager(), "categories", "domain_id");
    }

}

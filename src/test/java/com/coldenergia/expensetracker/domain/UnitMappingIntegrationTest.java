package com.coldenergia.expensetracker.domain;

import org.junit.Test;

import static com.coldenergia.expensetracker.domain.JpaAssertions.assertTableExists;
import static com.coldenergia.expensetracker.domain.JpaAssertions.assertTableHasColumn;

/**
 * User: coldenergia
 * Date: 5/2/14
 * Time: 7:22 PM
 */
public class UnitMappingIntegrationTest extends MappingIntegrationTest {

    @Test
    public void shouldCreateUnitsTable() {
        assertTableExists(getEntityManager(), "units");
    }

    @Test
    public void shouldCreateColumnsForUnitsTable() {
        assertTableHasColumn(getEntityManager(), "units", "id");
        assertTableHasColumn(getEntityManager(), "units", "name");
    }

}

package com.coldenergia.expensetracker.domain;

import org.junit.Test;

import static com.coldenergia.expensetracker.domain.JpaAssertions.assertTableExists;
import static com.coldenergia.expensetracker.domain.JpaAssertions.assertTableHasColumn;

/**
 * User: coldenergia
 * Date: 5/2/14
 * Time: 2:19 PM
 */
public class UserMappingIntegrationTest extends MappingIntegrationTest {

    @Test
    public void shouldCreateUsersTable() {
        assertTableExists(getEntityManager(), "users");
    }

    @Test
    public void shouldCreateColumnsForUsersTable() {
        assertTableHasColumn(getEntityManager(), "users", "id");
        assertTableHasColumn(getEntityManager(), "users", "name");
        assertTableHasColumn(getEntityManager(), "users", "created_date");
    }

}

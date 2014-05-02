package com.coldenergia.expensetracker.domain;

import org.junit.Test;

import static com.coldenergia.expensetracker.domain.JpaAssertions.assertTableExists;
import static com.coldenergia.expensetracker.domain.JpaAssertions.assertTableHasColumn;

/**
 * User: coldenergia
 * Date: 5/2/14
 * Time: 6:07 PM
 */
public class AuthorityMappingIntegrationTest extends MappingIntegrationTest {

    @Test
    public void shouldCreateAuthoritiesTable() {
        assertTableExists(getEntityManager(), "authorities");
    }

    @Test
    public void shouldCreateUserAuthoritiesTable() {
        assertTableExists(getEntityManager(), "user_authorities");
    }

    @Test
    public void shouldCreateColumnsForAuthoritiesTable() {
        assertTableHasColumn(getEntityManager(), "authorities", "id");
        assertTableHasColumn(getEntityManager(), "authorities", "name");
    }

    @Test
    public void shouldCreateColumnsForUserAuthoritiesTable() {
        // assertTableHasColumn(getEntityManager(), "user_authorities", "id");
        assertTableHasColumn(getEntityManager(), "user_authorities", "user_id");
        assertTableHasColumn(getEntityManager(), "user_authorities", "authority_id");
    }

}

package com.coldenergia.expensetracker.domain;

import org.junit.Test;

import static com.coldenergia.expensetracker.domain.JpaAssertions.assertTableExists;
import static com.coldenergia.expensetracker.domain.JpaAssertions.assertTableHasColumn;

/**
 * User: coldenergia
 * Date: 5/2/14
 * Time: 4:23 PM
 */
public class DomainMappingIntegrationTest extends MappingIntegrationTest {

    @Test
    public void shouldCreateDomainsTable() {
        assertTableExists(getEntityManager(), "domains");
    }

    @Test
    public void shouldCreateUserDomainsTable() {
        assertTableExists(getEntityManager(), "user_domains");
    }

    @Test
    public void shouldCreateColumnsForDomainsTable() {
        assertTableHasColumn(getEntityManager(), "domains", "id");
        assertTableHasColumn(getEntityManager(), "domains", "name");
    }

    @Test
    public void shouldCreateColumnsForUserDomainsTable() {
        /* I have commented out the 'id' column check as for testing database,
        * since the schema for it is created automatically by Hibernate */
        // assertTableHasColumn(getEntityManager(), "user_domains", "id");
        assertTableHasColumn(getEntityManager(), "user_domains", "user_id");
        assertTableHasColumn(getEntityManager(), "user_domains", "domain_id");
    }

}

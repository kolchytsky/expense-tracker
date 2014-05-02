package com.coldenergia.expensetracker.domain;

import com.coldenergia.expensetracker.config.JpaConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

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

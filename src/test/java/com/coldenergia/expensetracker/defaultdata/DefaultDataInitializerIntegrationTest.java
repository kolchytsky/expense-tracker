package com.coldenergia.expensetracker.defaultdata;

import com.coldenergia.expensetracker.internal.IntegrationTestsConfiguration;
import com.coldenergia.expensetracker.repository.AuthorityRepository;
import com.coldenergia.expensetracker.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertEquals;

/**
 * User: coldenergia
 * Date: 5/10/14
 * Time: 6:30 PM
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {
        IntegrationTestsConfiguration.class,
        DefaultDataInitializerIntegrationTest.TestConfiguration.class
})
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class DefaultDataInitializerIntegrationTest {

    @Configuration
    @ComponentScan(basePackages = { "com.coldenergia.expensetracker.defaultdata" })
    public static class TestConfiguration {
    }

    @Autowired
    private DefaultDataInitializer defaultDataInitializer;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthorityRepository authorityRepository;

    @Test
    public void shouldCreateDefaultAdminUserIfThereIsntOne() {
        Long initialCount = userRepository.count();
        defaultDataInitializer.insertInitialDataIntoDb();
        Long finalCount = userRepository.count();
        assertEquals(1L, (long) finalCount - initialCount);
    }

    @Test
    public void shouldCreateAuthoritiesIfThereArentAny() {
        // Delete the test data which is already present in the database
        userRepository.deleteAll();
        authorityRepository.deleteAll();

        Long initialCount = authorityRepository.count();
        defaultDataInitializer.insertInitialDataIntoDb();
        Long finalCount = authorityRepository.count();
        assertEquals(2L, (long) finalCount - initialCount);
    }

}

package com.coldenergia.expensetracker.defaultdata;

import com.coldenergia.expensetracker.config.JpaConfiguration;
import com.coldenergia.expensetracker.config.SecurityConfiguration;
import com.coldenergia.expensetracker.repository.AuthorityRepository;
import com.coldenergia.expensetracker.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
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
@ContextConfiguration(classes = { JpaConfiguration.class, SecurityConfiguration.class })
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class DefaultDataInitializerIntegrationTest {

    @Autowired
    private DefaultDataInitializer defaultDataInitializer;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthorityRepository authorityRepository;

    @Test
    public void shouldCreateDefaultAdminUserIfThereIsntOne() {
        defaultDataInitializer.insertInitialDataIntoDb();
        Long userCount = userRepository.count();
        assertEquals(1L, (long) userCount);
    }

    @Test
    public void shouldCreateAuthoritiesIfThereArentAny() {
        defaultDataInitializer.insertInitialDataIntoDb();
        Long authorityCount = authorityRepository.count();
        assertEquals(2L, (long) authorityCount);
    }

}

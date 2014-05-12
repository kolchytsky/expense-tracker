package com.coldenergia.expensetracker.service;

import com.coldenergia.expensetracker.builder.AuthorityBuilder;
import com.coldenergia.expensetracker.config.JpaConfiguration;
import com.coldenergia.expensetracker.domain.Authority;
import com.coldenergia.expensetracker.repository.AuthorityRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * User: coldenergia
 * Date: 5/12/14
 * Time: 10:31 PM
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { JpaConfiguration.class })
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class AuthorityServiceIntegrationTest {

    @Autowired
    private AuthorityService authorityService;

    @Autowired
    private AuthorityRepository authorityRepository;

    @Test
    public void shouldSaveAuthority() {
        Authority authority = new AuthorityBuilder().build();
        assertNull(authority.getId());
        long initialAuthorityCount = authorityRepository.count();
        authorityService.save(authority);
        long finalAuthorityCount = authorityRepository.count();
        // A delta assertion
        assertEquals(1L, finalAuthorityCount - initialAuthorityCount);
        assertNotNull(authority.getId());
    }

}

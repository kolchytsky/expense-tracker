package com.coldenergia.expensetracker.service;

import com.coldenergia.expensetracker.builder.AuthorityBuilder;
import com.coldenergia.expensetracker.config.test.IntegrationTestsConfiguration;
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
import static org.junit.Assert.fail;

/**
 * User: coldenergia
 * Date: 5/12/14
 * Time: 10:31 PM
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { IntegrationTestsConfiguration.class })
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

    @Test
    public void shouldNotSaveAuthorityWithNonUniqueName() {
        Authority prototype = new AuthorityBuilder().withName("prototype").build();
        authorityRepository.save(prototype);
        Authority prototypeClone = new AuthorityBuilder().withName("prototype").build();
        try {
            authorityRepository.save(prototypeClone);
            fail("Should've thrown an exception when attempting to save an authority with a non-unique name");
        } catch (Exception expected) {}
    }

}

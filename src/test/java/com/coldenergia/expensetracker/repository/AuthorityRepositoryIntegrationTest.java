package com.coldenergia.expensetracker.repository;

import com.coldenergia.expensetracker.builder.AuthorityBuilder;
import com.coldenergia.expensetracker.config.JpaConfiguration;
import com.coldenergia.expensetracker.domain.Authority;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

/**
 * User: coldenergia
 * Date: 5/3/14
 * Time: 9:09 PM
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {JpaConfiguration.class})
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class AuthorityRepositoryIntegrationTest {

    @Autowired
    private AuthorityRepository authorityRepository;

    @Test
    public void shouldCreateAuthority() {
        Authority authority = new AuthorityBuilder().build();
        Authority retrievedAuthority = authorityRepository.save(authority);
        assertNotNull(retrievedAuthority);
        assertEquals(authority, retrievedAuthority);
    }

}

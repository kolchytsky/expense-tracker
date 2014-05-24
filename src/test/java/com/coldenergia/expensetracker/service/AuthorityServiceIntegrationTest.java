package com.coldenergia.expensetracker.service;

import com.coldenergia.expensetracker.builder.AuthorityBuilder;
import com.coldenergia.expensetracker.domain.Authority;
import com.coldenergia.expensetracker.repository.AuthorityRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

/**
 * User: coldenergia
 * Date: 5/12/14
 * Time: 10:31 PM
 */
public class AuthorityServiceIntegrationTest extends ServiceIntegrationTest {

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

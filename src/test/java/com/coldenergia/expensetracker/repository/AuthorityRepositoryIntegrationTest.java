package com.coldenergia.expensetracker.repository;

import com.coldenergia.expensetracker.builder.AuthorityBuilder;
import com.coldenergia.expensetracker.domain.Authority;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

/**
 * User: coldenergia
 * Date: 5/3/14
 * Time: 9:09 PM
 */
public class AuthorityRepositoryIntegrationTest extends RepositoryIntegrationTest {

    @Autowired
    private AuthorityRepository authorityRepository;

    @Test
    public void shouldCreateAuthority() {
        Authority authority = new AuthorityBuilder().build();
        Authority retrievedAuthority = authorityRepository.save(authority);
        assertNotNull(retrievedAuthority);
        assertEquals(authority, retrievedAuthority);
    }

    @Test
    public void shouldFindAuthorityByName() {
        Authority authority = new AuthorityBuilder().withName("destroyer").build();
        authorityRepository.save(authority);
        Authority retrievedAuthority = authorityRepository.findByName("destroyer");
        assertNotNull(retrievedAuthority);
        assertEquals(authority, retrievedAuthority);
    }

}

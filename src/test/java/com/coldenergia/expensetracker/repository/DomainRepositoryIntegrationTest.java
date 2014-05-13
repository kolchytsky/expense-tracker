package com.coldenergia.expensetracker.repository;

import com.coldenergia.expensetracker.builder.DomainBuilder;
import com.coldenergia.expensetracker.domain.Domain;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

/**
 * User: coldenergia
 * Date: 5/3/14
 * Time: 3:32 PM
 */
public class DomainRepositoryIntegrationTest extends RepositoryIntegrationTest {

    @Autowired
    private DomainRepository domainRepository;

    @Test
    public void shouldSaveDomain() {
        Domain domain = new DomainBuilder().build();
        Domain retrievedDomain = domainRepository.save(domain);
        assertNotNull(retrievedDomain);
        assertEquals(domain, retrievedDomain);
    }

}

package com.coldenergia.expensetracker.repository;

import com.coldenergia.expensetracker.builder.DomainBuilder;
import com.coldenergia.expensetracker.domain.Domain;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.coldenergia.expensetracker.internal.test.data.TestDataInitializer.users;
import static org.junit.Assert.*;

import static com.coldenergia.expensetracker.internal.test.data.TestDataInitializer.THORAX;

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

    @Test
    public void shouldFindDomainsAccessibleByUserByUserId() {
        Domain accessible = new DomainBuilder().withName("accessible").withUser(users(THORAX)).build();
        Domain inaccessible = new DomainBuilder().withName("inaccessible").build();
        domainRepository.save(accessible);
        domainRepository.save(inaccessible);

        List<Domain> domains = domainRepository.findDomainsAccessibleByUser(users(THORAX).getId());

        assertFalse(domains.isEmpty());
        assertTrue(domains.contains(accessible));
        assertFalse(domains.contains(inaccessible));
    }

    @Test
    public void shouldFindDomainsAccessibleByUserByUserName() {
        Domain accessible = new DomainBuilder().withName("accessible").withUser(users(THORAX)).build();
        Domain inaccessible = new DomainBuilder().withName("inaccessible").build();
        domainRepository.save(accessible);
        domainRepository.save(inaccessible);

        List<Domain> domains = domainRepository.findDomainsAccessibleByUser(users(THORAX).getName());

        assertFalse(domains.isEmpty());
        assertTrue(domains.contains(accessible));
        assertFalse(domains.contains(inaccessible));
    }

    @Test
    public void shouldFindOneAccessibleByUser() {
        Domain accessible = new DomainBuilder().withName("accessible").withUser(users(THORAX)).build();
        Domain inaccessible = new DomainBuilder().withName("inaccessible").build();
        domainRepository.save(accessible);
        domainRepository.save(inaccessible);

        String userName = users(THORAX).getName();

        Domain accessibleResult = domainRepository.findOneAccessibleByUser(accessible.getId(), userName);
        assertNotNull(accessibleResult);
        assertEquals(accessible, accessibleResult);

        Domain inaccessibleResult = domainRepository.findOneAccessibleByUser(inaccessible.getId(), userName);
        assertNull(inaccessibleResult);
    }

}

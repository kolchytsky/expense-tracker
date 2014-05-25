package com.coldenergia.expensetracker.service.domain;

import com.coldenergia.expensetracker.builder.DomainBuilder;
import com.coldenergia.expensetracker.domain.Domain;
import com.coldenergia.expensetracker.repository.CategoryRepository;
import com.coldenergia.expensetracker.repository.DomainRepository;
import com.coldenergia.expensetracker.service.DomainNameIsTakenException;
import com.coldenergia.expensetracker.service.DomainService;
import com.coldenergia.expensetracker.service.ServiceIntegrationTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

// TODO: Think about a method to test setDomainUsers
/**
 * User: coldenergia
 * Date: 5/24/14
 * Time: 10:50 AM
 */
public class DomainServiceIntegrationTest extends ServiceIntegrationTest {

    @Autowired
    private DomainRepository domainRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private DomainService domainService;

    @Test
    public void shouldSaveDomain() {
        Domain domain = new DomainBuilder().build();
        long initialCount = domainRepository.count();
        domainService.save(domain);
        long finalCount = domainRepository.count();
        assertEquals(1L, finalCount - initialCount);
        assertNotNull(domain.getId());
    }

    @Test
    public void shouldNotSaveDomainWithNonUniqueName() {
        Domain junkyard = new DomainBuilder().withName("Junkyard").build();
        domainService.save(junkyard);
        Domain junkyardClone = new DomainBuilder().withName("Junkyard").build();
        try {
            domainService.save(junkyardClone);
            fail("Should've thrown an exception when attempting to save a domain with a non-unique name");
        } catch (DomainNameIsTakenException expected) {}
    }

    @Test
    public void shouldCreateRootCategory() {
        Domain domain = new DomainBuilder().build();
        long initialCategoryCount = categoryRepository.count();
        domainService.save(domain);
        long finalCategoryCount = categoryRepository.count();
        assertEquals(1L, finalCategoryCount - initialCategoryCount);
    }

}

package com.coldenergia.expensetracker.repository;

import com.coldenergia.expensetracker.builder.DomainBuilder;
import com.coldenergia.expensetracker.config.JpaConfiguration;
import com.coldenergia.expensetracker.domain.Domain;
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
 * Time: 3:32 PM
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {JpaConfiguration.class})
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class DomainRepositoryIntegrationTest {

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

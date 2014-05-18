package com.coldenergia.expensetracker.domain;

import com.coldenergia.expensetracker.config.test.IntegrationTestsConfiguration;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

/**
 * User: coldenergia
 * Date: 5/2/14
 * Time: 5:00 PM
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { IntegrationTestsConfiguration.class })
@Transactional
@TransactionConfiguration(defaultRollback = true)
public abstract class MappingIntegrationTest {

    @Autowired
    private EntityManager entityManager;

    public EntityManager getEntityManager() {
        return entityManager;
    }

}

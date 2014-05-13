package com.coldenergia.expensetracker.repository;

import com.coldenergia.expensetracker.config.JpaConfiguration;
import com.coldenergia.expensetracker.config.SecurityConfiguration;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

/**
 * User: coldenergia
 * Date: 5/13/14
 * Time: 9:04 PM
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { JpaConfiguration.class, SecurityConfiguration.class })
@Transactional
@TransactionConfiguration(defaultRollback = true)
public abstract class RepositoryIntegrationTest {
}

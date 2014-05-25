package com.coldenergia.expensetracker.service;

import com.coldenergia.expensetracker.internal.IntegrationTestsConfiguration;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

/**
 * User: coldenergia
 * Date: 5/24/14
 * Time: 10:51 AM
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { IntegrationTestsConfiguration.class })
@Transactional
@TransactionConfiguration(defaultRollback = true)
public abstract class ServiceIntegrationTest {
}

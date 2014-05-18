package com.coldenergia.expensetracker.config.test;

import com.coldenergia.expensetracker.config.JpaConfiguration;
import com.coldenergia.expensetracker.config.SecurityConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * This configuration is used for integration tests.<br>
 * User: coldenergia
 * Date: 5/18/14
 * Time: 1:01 PM
 */
@Configuration
@Import({
        TestDataSourceConfiguration.class,
        JpaConfiguration.class,
        SecurityConfiguration.class
})
public class IntegrationTestsConfiguration {}

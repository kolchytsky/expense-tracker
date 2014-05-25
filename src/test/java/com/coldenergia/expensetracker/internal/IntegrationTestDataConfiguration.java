package com.coldenergia.expensetracker.internal;

import com.coldenergia.expensetracker.internal.test.data.TestDataInitializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * User: coldenergia
 * Date: 5/25/14
 * Time: 2:05 PM
 */
@Configuration
@ComponentScan(basePackages = { "com.coldenergia.expensetracker.internal.test.data" })
public class IntegrationTestDataConfiguration {

    @Autowired
    private TestDataInitializer testDataInitializer;

    /**
     * Inserts data for integration tests after the creation of this configuration bean.
     * */
    @PostConstruct
    public void insertTestData() {
        testDataInitializer.insertTestDataIntoDb();
    }

}

package com.coldenergia.expensetracker.config;

import com.coldenergia.expensetracker.defaultdata.DefaultDataInitializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * User: coldenergia
 * Date: 5/18/14
 * Time: 9:30 AM
 */
@Configuration
@ComponentScan(basePackages = { "com.coldenergia.expensetracker.defaultdata" })
public class DefaultDataConfiguration {

    @Autowired
    private DefaultDataInitializer defaultDataInitializer;

    /**
     * Inserts initial data into the database when this configuration bean is created.
     * */
    @PostConstruct
    public void initDatabase() {
        defaultDataInitializer.insertInitialDataIntoDb();
    }

}

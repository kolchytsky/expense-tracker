package com.coldenergia.expensetracker.config.production;

import com.coldenergia.expensetracker.config.DefaultDataConfiguration;
import com.coldenergia.expensetracker.config.JpaConfiguration;
import com.coldenergia.expensetracker.config.SecurityConfiguration;
import org.springframework.context.annotation.Import;

/**
 * User: coldenergia
 * Date: 5/18/14
 * Time: 2:32 PM
 */
@Import({
        ProductionDataSourceConfiguration.class,
        JpaConfiguration.class,
        DefaultDataConfiguration.class,
        SecurityConfiguration.class
})
public class ProductionSecurityConfiguration {}

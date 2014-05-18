package com.coldenergia.expensetracker.config.initializer;

import com.coldenergia.expensetracker.config.production.ProductionSecurityConfiguration;
import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

/**
 * User: coldenergia
 * Date: 5/5/14
 * Time: 9:59 PM
 */
public class SecurityWebApplicationInitializer extends AbstractSecurityWebApplicationInitializer {

    public SecurityWebApplicationInitializer() {
        super(ProductionSecurityConfiguration.class);
    }

}

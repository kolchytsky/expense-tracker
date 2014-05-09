package com.coldenergia.expensetracker.config;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

/**
 * User: coldenergia
 * Date: 5/5/14
 * Time: 9:59 PM
 */
public class SecurityWebApplicationInitializer extends AbstractSecurityWebApplicationInitializer {

    public SecurityWebApplicationInitializer() {
        super(SecurityConfiguration.class);
    }

}

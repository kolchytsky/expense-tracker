package com.coldenergia.expensetracker.config;

import com.coldenergia.expensetracker.service.UserDetailsServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * User: coldenergia
 * Date: 5/5/14
 * Time: 8:57 PM
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { SecurityConfiguration.class, JpaConfiguration.class })
@WebAppConfiguration
public class SecurityConfigurationTest {

    @Autowired
    private org.springframework.security.web.FilterChainProxy springSecurityFilterChain;

    @Autowired
    private AuthenticationManagerBuilder auth;

    @Test
    public void shouldLoadSecurityConfiguration() {}

    @Test
    public void shouldUseCustomUserDetailsService() {
        assertNotNull(auth.getDefaultUserDetailsService());
        assertTrue(auth.getDefaultUserDetailsService() instanceof UserDetailsServiceImpl);
    }

}

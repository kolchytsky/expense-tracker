package com.coldenergia.expensetracker.config;

import com.coldenergia.expensetracker.internal.TestDataSourceConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.junit.Assert.assertNotNull;

/**
 * User: coldenergia
 * Date: 5/5/14
 * Time: 8:57 PM
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {
        TestDataSourceConfiguration.class,
        JpaConfiguration.class,
        SecurityConfiguration.class
})
@WebAppConfiguration
public class SecurityConfigurationTest {

    @Autowired
    private FilterChainProxy springSecurityFilterChain;

    @Autowired
    private AuthenticationManagerBuilder auth;

    @Test
    public void shouldLoadSecurityConfiguration() {
        assertNotNull(auth.getDefaultUserDetailsService());
    }

    /*@Test
    public void shouldUseCustomUserDetailsService() {
        assertNotNull(auth.getDefaultUserDetailsService());
        // After having marked UserDetailsServiceImpl as @Transactional, this thing here is now wrapped in proxy...
        // assertTrue(auth.getDefaultUserDetailsService() instanceof UserDetailsServiceImpl);
    }*/

}

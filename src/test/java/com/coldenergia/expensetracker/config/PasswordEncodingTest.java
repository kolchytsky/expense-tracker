package com.coldenergia.expensetracker.config;

import com.coldenergia.expensetracker.builder.UserSecurityDetailsBuilder;
import com.coldenergia.expensetracker.domain.UserSecurityDetails;
import com.coldenergia.expensetracker.service.DomainService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * User: coldenergia
 * Date: 5/9/14
 * Time: 11:51 AM
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { PasswordEncodingTest.TestConfig.class })
public class PasswordEncodingTest {

    @Configuration
    @Import({ SecurityConfiguration.class })
    public static class TestConfig {

        @Bean
        public UserDetailsService userDetailsService() {
            return mock(UserDetailsService.class);
        }

        @Bean
        public DomainService domainService() {
            return mock(DomainService.class);
        }

    }

    @Autowired
    private SecurityConfiguration securityConfiguration;

    /*@Autowired
    private PasswordEncoder passwordEncoder;*/

    @Autowired
    private UserDetailsService userDetailsService;

    @Before
    public void setUp() {
        initMocks(this);
    }

    @Test
    public void shouldUseBcryptPasswordEncoder() throws Exception {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode("Skaarj");
        UserSecurityDetails userDetails = new UserSecurityDetailsBuilder().withUsername("Gkublok").withPassword(encodedPassword).build();
        when(userDetailsService.loadUserByUsername("Gkublok")).thenReturn(userDetails);
        AuthenticationManager am = securityConfiguration.authenticationManagerBean();
        am.authenticate(new UsernamePasswordAuthenticationToken("Gkublok", "Skaarj"));
    }

}

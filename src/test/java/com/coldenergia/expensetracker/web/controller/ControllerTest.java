package com.coldenergia.expensetracker.web.controller;

import com.coldenergia.expensetracker.builder.CsrfTokenBuilder;
import com.coldenergia.expensetracker.config.SecurityConfiguration;
import com.coldenergia.expensetracker.domain.UserSecurityDetails;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static com.coldenergia.expensetracker.defaultdata.DefaultDataConstants.*;
import static com.coldenergia.expensetracker.web.CsrfConstants.CSRF_TOKEN_VALUE_FOR_TEST;
import static com.coldenergia.expensetracker.web.CsrfConstants.DEFAULT_CSRF_PARAMETER_NAME;
import static com.coldenergia.expensetracker.web.CsrfConstants.DEFAULT_CSRF_TOKEN_ATTR_NAME;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * User: coldenergia
 * Date: 5/9/14
 * Time: 9:49 PM
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {
        ControllerTest.ControllerTestConfiguration.class,
        com.coldenergia.expensetracker.config.WebAppConfiguration.class,
        SecurityConfiguration.class
})
@WebAppConfiguration
public abstract class ControllerTest {

    @Configuration
    public static class ControllerTestConfiguration {

        @Bean
        public UserDetailsService userDetailsService() {
            UserDetailsService userDetailsService = mock(UserDetailsService.class);

            UserDetails adminDetails = constructAdminDetails();
            when(userDetailsService.loadUserByUsername(DEFAULT_ADMIN_NAME)).thenReturn(adminDetails);

            UserDetails spenderDetails = constructSpenderDetails();
            when(userDetailsService.loadUserByUsername(THORAX)).thenReturn(spenderDetails);

            return userDetailsService;
        }

        private UserDetails constructAdminDetails() {
            UserSecurityDetails adminDetails = new UserSecurityDetails();
            adminDetails.setUsername(DEFAULT_ADMIN_NAME);
            adminDetails.setPassword(new BCryptPasswordEncoder().encode(DEFAULT_ADMIN_PASSWORD));
            List<String> adminAuthorityNames = new ArrayList<String>(1);
            adminAuthorityNames.add(ADMIN_AUTHORITY_NAME);
            adminDetails.setAuthorities(adminAuthorityNames);
            return adminDetails;
        }

        private UserDetails constructSpenderDetails() {
            UserSecurityDetails spenderDetails = new UserSecurityDetails();
            spenderDetails.setUsername(THORAX);
            List<String> spenderAuthorityNames = new ArrayList<String>(1);
            spenderAuthorityNames.add(SPENDER_AUTHORITY_NAME);
            spenderDetails.setAuthorities(spenderAuthorityNames);
            return spenderDetails;
        }

    }

    /**
     * Relentless in competition and possessed of a digital immortality
     * that the organics are incapable of understanding, Thorax seeks only one goal: victory.<br>
     * Actually, Thorax spends lots of money on upgrades and weaponry. So, this is
     * a default name for a spender user in test context.
     * */
    public static final String THORAX = "Thorax";

    @Autowired
    protected WebApplicationContext wac;

    @Autowired
    protected FilterChainProxy springSecurityFilterChain;

    /**
     * Adds CsrfToken as a request parameter and as a session attribute
     * (so that {@link org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository} would
     * be able to check the provided request parameter against this token). The solution has been nicely
     * described in this
     * <a href="http://www.petrikainulainen.net/programming/spring-framework/adding-social-sign-in-to-a-spring-mvc-web-application-registration-and-login/">blog post</a>
     * and documented in this
     * <a href="https://github.com/pkainulainen/spring-social-examples/blob/master/sign-in/spring-mvc-normal/src/integration-test/java/net/petrikainulainen/spring/social/signinmvc/security/CsrfTokenBuilder.java">github repository</a>.
     * */
    protected static MockHttpServletRequestBuilder addCsrfToken(MockHttpServletRequestBuilder requestBuilder) {
        return requestBuilder
                .param(DEFAULT_CSRF_PARAMETER_NAME, CSRF_TOKEN_VALUE_FOR_TEST)
                .sessionAttr(DEFAULT_CSRF_TOKEN_ATTR_NAME, new CsrfTokenBuilder().build());
    }

}

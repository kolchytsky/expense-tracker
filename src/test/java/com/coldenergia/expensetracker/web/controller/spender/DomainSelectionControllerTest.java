package com.coldenergia.expensetracker.web.controller.spender;

import com.coldenergia.expensetracker.builder.DomainBuilder;
import com.coldenergia.expensetracker.domain.Domain;
import com.coldenergia.expensetracker.service.DomainService;
import com.coldenergia.expensetracker.web.controller.ControllerTest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static com.coldenergia.expensetracker.defaultdata.DefaultDataConstants.DEFAULT_ADMIN_NAME;
import static com.coldenergia.expensetracker.web.util.SecurityRequestPostProcessors.userDetailsService;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

/**
 * User: coldenergia
 * Date: 5/30/14
 * Time: 8:34 PM
 */
@ContextConfiguration(classes = { DomainSelectionControllerTest.TestConfiguration.class })
public class DomainSelectionControllerTest extends ControllerTest {

    @Configuration
    public static class TestConfiguration {

        @Bean
        public DomainService domainService() {
            return mock(DomainService.class);
        }

    }

    private MockMvc mockMvc;

    @Autowired
    private DomainService domainService;

    @Before
    public void setup() {
        this.mockMvc = webAppContextSetup(this.wac).addFilters(springSecurityFilterChain).build();
    }

    @Test
    public void shouldShowDomainSelectionToSpender() throws Exception {
        mockMvc.perform(get("/domain-selection").with(userDetailsService(THORAX)))
                .andExpect(view().name("spender/domain-selection"))
                .andExpect(model().attribute("availableDomains", notNullValue()))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldNotShowDomainSelectionToAdmin() throws Exception {
        mockMvc.perform(get("/domain-selection").with(userDetailsService(DEFAULT_ADMIN_NAME)))
                .andExpect(status().isForbidden());
    }

    @Test
    public void shouldShowOnlyAccessibleDomainsInSelection() throws Exception {
        Domain accessible = new DomainBuilder().build();
        List<Domain> accessibleDomains = new ArrayList<>(1);
        accessibleDomains.add(accessible);
        when(domainService.findDomainsAccessibleByUser(THORAX)).thenReturn(accessibleDomains);

        mockMvc.perform(get("/domain-selection").with(userDetailsService(THORAX)))
                .andExpect(model().attribute("availableDomains", accessibleDomains));
    }

}

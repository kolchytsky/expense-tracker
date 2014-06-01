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

import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;
import static com.coldenergia.expensetracker.web.util.SecurityRequestPostProcessors.userDetailsService;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * User: coldenergia
 * Date: 5/31/14
 * Time: 11:53 AM
 */
@ContextConfiguration(classes = { DomainHomeControllerTest.TestConfiguration.class })
public class DomainHomeControllerTest extends ControllerTest {

    @Configuration
    public static class TestConfiguration {

        @Bean
        public DomainService domainService() {
            return mock(DomainService.class);
        }

    }

    @Autowired
    private DomainService domainService;

    @Before
    public void setup() {
        super.setup();
    }

    @Test
    public void shouldRenderDomainHomePageForSpender() throws Exception {
        Domain selectedDomain = new DomainBuilder().withId(4L).build();
        List<Domain> accessible = new ArrayList<>(1);
        accessible.add(selectedDomain);
        when(domainService.findDomainsAccessibleByUser(THORAX)).thenReturn(accessible);
        when(domainService.findOne(selectedDomain.getId())).thenReturn(selectedDomain);
        when(domainService.findOneAccessibleByUser(selectedDomain.getId(), THORAX)).thenReturn(selectedDomain);

        mockMvc.perform(get("/domains/" + selectedDomain.getId()).with(userDetailsService(THORAX)))
                .andExpect(view().name("spender/domain-home"))
                .andExpect(model().attribute("currentDomain", notNullValue()))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldNotRenderDomainHomePageForDomainInaccessibleBySpender() throws Exception {
        Domain inaccessible = new DomainBuilder().withId(4L).build();
        when(domainService.findDomainsAccessibleByUser(THORAX)).thenReturn(new ArrayList<Domain>());
        when(domainService.findOne(inaccessible.getId())).thenReturn(inaccessible);
        when(domainService.findOneAccessibleByUser(inaccessible.getId(), THORAX)).thenReturn(null);

        mockMvc.perform(get("/domains/" + inaccessible.getId()).with(userDetailsService(THORAX)))
                .andExpect(status().isForbidden());
    }

}

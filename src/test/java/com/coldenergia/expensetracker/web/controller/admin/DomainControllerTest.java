package com.coldenergia.expensetracker.web.controller.admin;

import com.coldenergia.expensetracker.builder.DomainBuilder;
import com.coldenergia.expensetracker.domain.Domain;
import com.coldenergia.expensetracker.service.DomainNameIsTakenException;
import com.coldenergia.expensetracker.service.DomainService;
import com.coldenergia.expensetracker.web.controller.ControllerTest;
import com.coldenergia.expensetracker.web.view.model.domain.DomainForm;
import com.coldenergia.expensetracker.web.view.model.domain.DomainUsersForm;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static com.coldenergia.expensetracker.defaultdata.DefaultDataConstants.DEFAULT_ADMIN_NAME;
import static com.coldenergia.expensetracker.web.util.SecurityRequestPostProcessors.userDetailsService;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

/**
 * User: coldenergia
 * Date: 5/24/14
 * Time: 2:39 PM
 */
@ContextConfiguration(classes = { DomainControllerTest.TestConfiguration.class })
public class DomainControllerTest extends ControllerTest {

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

    public static final String DOMAIN_FORM_NAME_PARAM = "name";

    @Before
    public void setup() {
        this.mockMvc = webAppContextSetup(this.wac).addFilters(springSecurityFilterChain).build();
    }

    @Test
    public void shouldShownNewDomainFormPage() throws Exception {
        mockMvc.perform(get("/admin/domains/new").with(userDetailsService(DEFAULT_ADMIN_NAME)))
                .andExpect(view().name("admin/domains/new-domain"))
                .andExpect(model().attribute("domainForm", notNullValue()))
                .andExpect(model().attribute("domainForm", is(DomainForm.class)))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldCreateNewDomain() throws Exception {
        mockMvc.perform(
                addCsrfToken(post("/admin/domains"))
                        .param(DOMAIN_FORM_NAME_PARAM, "Acatana")
                        .with(userDetailsService(DEFAULT_ADMIN_NAME)))
                .andExpect(status().isMovedTemporarily())
                .andExpect(redirectedUrl("/admin/domains"));
    }

    @Test
    public void shouldValidateBeforeCreatingNewDomain() throws Exception {
        mockMvc.perform(
                addCsrfToken(post("/admin/domains"))
                        .param(DOMAIN_FORM_NAME_PARAM, "")
                        .with(userDetailsService(DEFAULT_ADMIN_NAME)))
                .andExpect(view().name("admin/domains/new-domain"));
    }

    @Test
    public void shouldNotAllowAccessForSpender() throws Exception {
        mockMvc.perform(get("/admin/domains/new").with(userDetailsService(THORAX)))
                .andExpect(status().isForbidden());
        mockMvc.perform(
                addCsrfToken(post("/admin/domains"))
                        .param(DOMAIN_FORM_NAME_PARAM, "Acatana")
                        .with(userDetailsService(THORAX)))
                .andExpect(status().isForbidden());
    }

    @Test
    public void shouldRedirectToLoginIfUnauthenticated() throws Exception {
        mockMvc.perform(get("/admin/domains/new")).andExpect(redirectedUrlPattern("**/login*"));
    }

    @Test
    public void shouldShowListOfAllDomains() throws Exception {
        mockMvc.perform(get("/admin/domains")
                .with(userDetailsService(DEFAULT_ADMIN_NAME)))
                .andExpect(model().attribute("domains", notNullValue()))
                .andExpect(view().name("admin/domains/list-domains"));
    }

    @Test
    public void shouldShowSetDomainUsersFormPage() throws Exception {
        Domain domain = new DomainBuilder().withId(4L).build();
        when(domainService.findOne(4L)).thenReturn(domain);
        when(domainService.findOneAndInitUserList(4L)).thenReturn(domain);

        mockMvc.perform(get("/admin/domains/4/users/edit")
                .with(userDetailsService(DEFAULT_ADMIN_NAME)))
                .andExpect(model().attribute("domainUsersForm", notNullValue()))
                .andExpect(model().attribute("domainUsersForm", is(DomainUsersForm.class)))
                .andExpect(model().attribute("userMap", notNullValue()))
                .andExpect(model().attribute("domain", notNullValue()))
                .andExpect(view().name("admin/domains/edit-domain-users"));
    }

    @Test
    public void shouldInformUserAboutTakenDomainNameAndNotThrowingAnyExceptions() throws Exception {
        ArgumentMatcher<Domain> domainWithTakenNameMatcher = new ArgumentMatcher<Domain>() {
            @Override
            public boolean matches(Object argument) {
                return "Taken_name".equals(((Domain) argument).getName());
            }
        };

        when(domainService.save(argThat(domainWithTakenNameMatcher))).thenThrow(DomainNameIsTakenException.class);

        mockMvc.perform(
                addCsrfToken(post("/admin/domains"))
                        .param(DOMAIN_FORM_NAME_PARAM, "Taken_name")
                        .with(userDetailsService(DEFAULT_ADMIN_NAME)))
                .andExpect(view().name("admin/domains/new-domain"));
    }

}

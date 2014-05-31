package com.coldenergia.expensetracker.web.controller;

import com.coldenergia.expensetracker.builder.DomainBuilder;
import com.coldenergia.expensetracker.domain.Domain;
import com.coldenergia.expensetracker.service.DomainService;
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
import static com.coldenergia.expensetracker.defaultdata.DefaultDataConstants.DEFAULT_ADMIN_PASSWORD;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

/**
 * User: coldenergia
 * Date: 5/9/14
 * Time: 9:51 PM
 */
@ContextConfiguration(classes = { LoginControllerTest.TestConfiguration.class })
public class LoginControllerTest extends ControllerTest {

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
        //this.mockMvc = webAppContextSetup(this.wac).build().alwaysExpect(status().isOk()).build();
        this.mockMvc = webAppContextSetup(this.wac).addFilters(springSecurityFilterChain).build();
    }

    @Test
    public void shouldRenderLoginView() throws Exception {
        this.mockMvc.perform(get("/login")).andExpect(view().name("login")).andExpect(status().isOk());
    }

    @Test
    public void shouldRedirectToLoginForProtectedResources() throws Exception {
        this.mockMvc.perform(get("/")).andExpect(redirectedUrlPattern("**/login*"));
    }

    @Test
    public void shouldRedirectAdminToAdminMainPage() throws Exception {
        this.mockMvc.perform(
                addCsrfToken(post("/login"))
                        .param("username", DEFAULT_ADMIN_NAME)
                        .param("password", DEFAULT_ADMIN_PASSWORD))
                .andExpect(status().isMovedTemporarily())
                .andExpect(redirectedUrl("/admin"));
    }

    @Test
    public void shouldRedirectUserWhoHasAccessToMultipleDomainsToDomainSelectionPage() throws Exception {
        List<Domain> multipleDomains = new ArrayList<>(2);
        multipleDomains.add(new DomainBuilder().build());
        multipleDomains.add(new DomainBuilder().build());
        when(domainService.findDomainsAccessibleByUser(THORAX)).thenReturn(multipleDomains);

        this.mockMvc.perform(
                addCsrfToken(post("/login"))
                        .param("username", THORAX)
                        .param("password", THORAX_PASSWORD))
                .andExpect(status().isMovedTemporarily())
                .andExpect(redirectedUrl("/domain-selection"));
    }

    @Test
    public void shouldRedirectUserWhoHasAccessToNoDomainsToDomainSelectionPage() throws Exception {
        when(domainService.findDomainsAccessibleByUser(THORAX)).thenReturn(new ArrayList<Domain>());

        this.mockMvc.perform(
                addCsrfToken(post("/login"))
                        .param("username", THORAX)
                        .param("password", THORAX_PASSWORD))
                .andExpect(status().isMovedTemporarily())
                .andExpect(redirectedUrl("/domain-selection"));
    }

    @Test
    public void shouldRedirectUserWhoHasAccessToOnlyOneDomainToThisDomainsPage() throws Exception {
        Domain domain = new DomainBuilder().withId(4L).build();
        List<Domain> singleDomainList = new ArrayList<>(1);
        singleDomainList.add(domain);
        when(domainService.findDomainsAccessibleByUser(THORAX)).thenReturn(singleDomainList);

        this.mockMvc.perform(
                addCsrfToken(post("/login"))
                        .param("username", THORAX)
                        .param("password", THORAX_PASSWORD))
                .andExpect(status().isMovedTemporarily())
                .andExpect(redirectedUrl("/domains/" + domain.getId()));
    }

    @Test
    public void shouldNotAllowToLoginWithoutCsrfToken() throws Exception {
        this.mockMvc.perform(
                post("/login")
                        .param("username", DEFAULT_ADMIN_NAME)
                        .param("password", "mandible"))
                .andExpect(status().isForbidden());
    }

}

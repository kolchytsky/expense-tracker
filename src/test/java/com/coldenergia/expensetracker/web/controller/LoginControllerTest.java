package com.coldenergia.expensetracker.web.controller;

import org.junit.Before;
import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;

import static com.coldenergia.expensetracker.defaultdata.DefaultDataConstants.DEFAULT_ADMIN_NAME;
import static com.coldenergia.expensetracker.defaultdata.DefaultDataConstants.DEFAULT_ADMIN_PASSWORD;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

/**
 * User: coldenergia
 * Date: 5/9/14
 * Time: 9:51 PM
 */
public class LoginControllerTest extends ControllerTest {

    private MockMvc mockMvc;

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
    public void shouldNotAllowToLoginWithoutCsrfToken() throws Exception {
        this.mockMvc.perform(
                post("/login")
                        .param("username", DEFAULT_ADMIN_NAME)
                        .param("password", "mandible"))
                .andExpect(status().isForbidden());
    }

}

package com.coldenergia.expensetracker.web.controller;

import org.junit.Before;
import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

    /*@Test
    public void shouldRedirectAdminToAdminMainPage() throws Exception {
        this.mockMvc.perform(post("/login").param("username", "admin").param("password", "mandible"))
                .andExpect(view().name("admin/main"));
    }*/

}

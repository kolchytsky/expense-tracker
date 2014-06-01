package com.coldenergia.expensetracker.web.controller;

import org.junit.Before;
import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

/**
 * User: coldenergia
 * Date: 6/1/14
 * Time: 8:42 AM
 */
public class LogoutControllerTest extends ControllerTest {

    @Before
    public void setup() {
        super.setup();
    }

    @Test
    public void shouldLogout() throws Exception {
        mockMvc.perform(addCsrfToken(post("/logout")))
                .andExpect(status().isMovedTemporarily())
                .andExpect(redirectedUrl("/login?logout"));
    }

}

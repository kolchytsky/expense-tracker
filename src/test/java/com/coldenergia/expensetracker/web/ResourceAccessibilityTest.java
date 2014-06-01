package com.coldenergia.expensetracker.web;

import com.coldenergia.expensetracker.web.controller.ControllerTest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * User: coldenergia
 * Date: 5/10/14
 * Time: 11:40 AM
 */
public class ResourceAccessibilityTest extends ControllerTest {

    @Before
    public void setup() {
        super.setup();
    }

    @Test
    public void shouldGrantAccessToResourcesToEveryone() throws Exception {
        mockMvc.perform(get("/resources/css/expense-tracker.css")).andExpect(status().isOk());
        mockMvc.perform(get("/resources/js/expense-tracker.js")).andExpect(status().isOk());
    }

}

package com.coldenergia.expensetracker.config;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * User: coldenergia
 * Date: 5/9/14
 * Time: 4:09 PM
 */
// TODO: That's rather a bad test for configuration - it passed without DispatcherServlet mapping and WebAppInitializer...ask someone
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { SecurityConfiguration.class, JpaConfiguration.class })
@WebAppConfiguration
public class WebConfigurationTest {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void shouldReturn404ForNonExistingPage() throws Exception {
        this.mockMvc.perform(get("/mandible-is-inorganic")).andExpect(status().isNotFound());
    }

}

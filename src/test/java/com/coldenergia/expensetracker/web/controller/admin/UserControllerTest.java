package com.coldenergia.expensetracker.web.controller.admin;

import com.coldenergia.expensetracker.builder.CsrfTokenBuilder;
import com.coldenergia.expensetracker.web.controller.ControllerTest;
import com.coldenergia.expensetracker.web.view.model.UserForm;
import org.junit.Before;
import org.junit.Test;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.test.web.servlet.MockMvc;

import static com.coldenergia.expensetracker.defaultdata.DefaultDataConstants.DEFAULT_ADMIN_NAME;
import static com.coldenergia.expensetracker.web.CsrfConstants.CSRF_TOKEN_VALUE_FOR_TEST;
import static com.coldenergia.expensetracker.web.CsrfConstants.DEFAULT_CSRF_PARAMETER_NAME;
import static com.coldenergia.expensetracker.web.CsrfConstants.DEFAULT_CSRF_TOKEN_ATTR_NAME;
import static com.coldenergia.expensetracker.web.util.SecurityRequestPostProcessors.userDetailsService;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

/**
 * User: coldenergia
 * Date: 5/18/14
 * Time: 3:56 PM
 */
public class UserControllerTest extends ControllerTest {

    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = webAppContextSetup(this.wac).addFilters(springSecurityFilterChain).build();
    }

    @Test
    public void shouldShowNewUserFormForAdmin() throws Exception {
        mockMvc.perform(get("/admin/users/new").with(userDetailsService(DEFAULT_ADMIN_NAME)))
                .andExpect(view().name("admin/users/new-user"))
                .andExpect(model().attribute("userForm", notNullValue()))
                .andExpect(model().attribute("userForm", is(UserForm.class)))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldCreateNewUser() throws Exception {
        mockMvc.perform(
                post("/admin/users/new")
                        .param("name", "Gkublok")
                        .param("authority", "spender")
                        .param(DEFAULT_CSRF_PARAMETER_NAME, CSRF_TOKEN_VALUE_FOR_TEST)
                        .sessionAttr(DEFAULT_CSRF_TOKEN_ATTR_NAME, new CsrfTokenBuilder().build())
                .with(userDetailsService(DEFAULT_ADMIN_NAME)))
                .andExpect(status().isMovedTemporarily());
                //.andExpect(redirectedUrlPattern("**/users"));
    }

    @Test
    public void shouldNotAllowAccessForSpender() throws Exception {
        mockMvc.perform(get("/admin/users/new").with(userDetailsService(THORAX)))
                .andExpect(status().isForbidden());
    }

    @Test
    public void shouldRedirectToLoginIfUnauthenticated() throws Exception {
        mockMvc.perform(get("/admin/users/new")).andExpect(redirectedUrlPattern("**/login*"));
    }

}

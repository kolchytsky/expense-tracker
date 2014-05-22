package com.coldenergia.expensetracker.web.controller.admin;

import com.coldenergia.expensetracker.domain.User;
import com.coldenergia.expensetracker.service.UserNameIsTakenException;
import com.coldenergia.expensetracker.service.UserService;
import com.coldenergia.expensetracker.web.controller.ControllerTest;
import com.coldenergia.expensetracker.web.view.model.UserForm;
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
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

/**
 * User: coldenergia
 * Date: 5/18/14
 * Time: 3:56 PM
 */
@ContextConfiguration(classes = { UserControllerTest.TestConfiguration.class })
public class UserControllerTest extends ControllerTest {

    @Configuration
    public static class TestConfiguration {

        @Bean
        public UserService userService() {
            return mock(UserService.class);
        }

    }

    private MockMvc mockMvc;

    @Autowired
    private UserService userService;

    private static final String USER_FORM_NAME_PARAM = "name";

    private static final String USER_FORM_PASSWORD_PARAM = "password";

    private static final String USER_FORM_AUTHORITY_PARAM = "authority";

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
                addCsrfToken(post("/admin/users"))
                        .param(USER_FORM_NAME_PARAM, "Gkublok")
                        .param(USER_FORM_PASSWORD_PARAM, "mandible")
                        .param(USER_FORM_AUTHORITY_PARAM, "SPENDER")
                .with(userDetailsService(DEFAULT_ADMIN_NAME)))
                .andExpect(status().isMovedTemporarily())
                .andExpect(redirectedUrl("/admin/users"));
    }

    @Test
    public void shouldValidateBeforeCreatingNewUser() throws Exception {
        mockMvc.perform(
                addCsrfToken(post("/admin/users"))
                        .param(USER_FORM_NAME_PARAM, "")
                        .param(USER_FORM_PASSWORD_PARAM, "")
                        .param(USER_FORM_AUTHORITY_PARAM, "")
                        .with(userDetailsService(DEFAULT_ADMIN_NAME)))
                .andExpect(view().name("admin/users/new-user"));
    }

    @Test
    public void shouldNotAllowAccessForSpender() throws Exception {
        mockMvc.perform(get("/admin/users/new").with(userDetailsService(THORAX)))
                .andExpect(status().isForbidden());
        mockMvc.perform(
                addCsrfToken(post("/admin/users"))
                        .param(USER_FORM_NAME_PARAM, "Gkublok")
                        .param(USER_FORM_PASSWORD_PARAM, "mandible")
                        .param(USER_FORM_AUTHORITY_PARAM, "SPENDER")
                        .with(userDetailsService(THORAX)))
                .andExpect(status().isForbidden());
    }

    @Test
    public void shouldRedirectToLoginIfUnauthenticated() throws Exception {
        mockMvc.perform(get("/admin/users/new")).andExpect(redirectedUrlPattern("**/login*"));
    }

    @Test
    public void shouldInformUserAboutTakenUserNameAndNotThrowingAnyExceptions() throws Exception {
        ArgumentMatcher<User> userWithTakenNameMatcher = new ArgumentMatcher<User>() {
            @Override
            public boolean matches(Object argument) {
                return "Taken_name".equals(((User) argument).getName());
            }
        };

        when(userService.saveUserWithNewPassword(argThat(userWithTakenNameMatcher), anyString()))
                .thenThrow(UserNameIsTakenException.class);
        when(userService.saveUserWithNewPassword(argThat(userWithTakenNameMatcher), anySetOf(String.class), anyString()))
                .thenThrow(UserNameIsTakenException.class);

        mockMvc.perform(
                addCsrfToken(post("/admin/users"))
                        .param(USER_FORM_NAME_PARAM, "Taken_name")
                        .param(USER_FORM_PASSWORD_PARAM, "mandible")
                        .param(USER_FORM_AUTHORITY_PARAM, "SPENDER")
                        .with(userDetailsService(DEFAULT_ADMIN_NAME)))
                .andExpect(view().name("admin/users/new-user"));
    }

}

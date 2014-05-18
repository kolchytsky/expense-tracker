package com.coldenergia.expensetracker.defaultdata;

import com.coldenergia.expensetracker.builder.AuthorityBuilder;
import com.coldenergia.expensetracker.builder.UserBuilder;
import com.coldenergia.expensetracker.domain.Authority;
import com.coldenergia.expensetracker.domain.User;
import com.coldenergia.expensetracker.service.AuthorityService;
import com.coldenergia.expensetracker.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.util.List;

import static com.coldenergia.expensetracker.defaultdata.DefaultDataConstants.*;
import static org.junit.Assert.assertEquals;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;

import static org.mockito.Mockito.*;

/**
 * User: coldenergia
 * Date: 5/10/14
 * Time: 5:52 PM
 */
public class DefaultDataInitializerTest {

    private DefaultDataInitializer defaultDataInitializer;

    private UserService userService;

    private AuthorityService authorityService;

    @Before
    public void setup() {
        userService = mock(UserService.class);
        authorityService = mock(AuthorityService.class);
        defaultDataInitializer = new DefaultDataInitializer(userService, authorityService);
    }

    @Test
    public void shouldCreateDefaultAdminUserIfThereIsntOne() {
        defaultDataInitializer.insertInitialDataIntoDb();
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(userService).saveUserWithNewPassword(userArgumentCaptor.capture(), eq(DEFAULT_ADMIN_PASSWORD));
        User admin = userArgumentCaptor.getValue();
        assertEquals(admin.getName(), DEFAULT_ADMIN_NAME);
    }

    @Test
    public void shouldNotAttemptToCreateDefaultAdminUserIfThereIsOne() {
        User defaultAdmin = new UserBuilder().withName(DEFAULT_ADMIN_NAME).build();
        when(userService.findByName(DEFAULT_ADMIN_NAME)).thenReturn(defaultAdmin);
        defaultDataInitializer.insertInitialDataIntoDb();
        verify(userService, never()).save(any(User.class));
    }

    @Test
    public void shouldCreateDefaultAuthoritiesIfThereArentAny() {
        defaultDataInitializer.insertInitialDataIntoDb();
        ArgumentCaptor<Authority> authorityArgumentCaptor = ArgumentCaptor.forClass(Authority.class);
        // Verify that the save method is called two times
        verify(authorityService, times(2)).save(authorityArgumentCaptor.capture());
        List<Authority> capturedAuthorities = authorityArgumentCaptor.getAllValues();
        assertEquals(2, capturedAuthorities.size());
        for (Authority authority : capturedAuthorities) {
            String name = authority.getName();
            boolean nameIsValid = name.equals(ADMIN_AUTHORITY_NAME) || name.equals(SPENDER_AUTHORITY_NAME);
            assertTrue(nameIsValid);
        }
    }

    @Test
    public void shouldNotAttemptToCreateAuthoritiesIfTheyExist() {
        Authority adminAuthority = new AuthorityBuilder().withName(ADMIN_AUTHORITY_NAME).build();
        when(authorityService.findByName(ADMIN_AUTHORITY_NAME)).thenReturn(adminAuthority);

        Authority userAuthority = new AuthorityBuilder().withName(SPENDER_AUTHORITY_NAME).build();
        when(authorityService.findByName(SPENDER_AUTHORITY_NAME)).thenReturn(userAuthority);

        defaultDataInitializer.insertInitialDataIntoDb();
        verify(authorityService, never()).save(any(Authority.class));
        verify(authorityService, never()).save(any(Iterable.class));
    }

}

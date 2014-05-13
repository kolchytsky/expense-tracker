package com.coldenergia.expensetracker.defaultdata;

import com.coldenergia.expensetracker.builder.UserBuilder;
import com.coldenergia.expensetracker.domain.User;
import com.coldenergia.expensetracker.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import static org.junit.Assert.assertEquals;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyObject;

import static com.coldenergia.expensetracker.defaultdata.DefaultDataConstants.DEFAULT_ADMIN_NAME;
import static org.mockito.Mockito.*;

/**
 * User: coldenergia
 * Date: 5/10/14
 * Time: 5:52 PM
 */
public class DefaultDataInitializerTest {

    private DefaultDataInitializer defaultDataInitializer;

    private UserService userService;

    @Before
    public void setup() {
        userService = mock(UserService.class);
        defaultDataInitializer = new DefaultDataInitializer(userService);
    }

    @Test
    public void shouldCreateDefaultAdminUserIfThereIsntOne() {
        defaultDataInitializer.insertInitialDataIntoDb();
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(userService).save(userArgumentCaptor.capture());
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

}

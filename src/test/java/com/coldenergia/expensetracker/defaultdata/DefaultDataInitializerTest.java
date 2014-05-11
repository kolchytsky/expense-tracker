package com.coldenergia.expensetracker.defaultdata;

import com.coldenergia.expensetracker.config.JpaConfiguration;
import com.coldenergia.expensetracker.defaultdata.DefaultDataInitializer;
import com.coldenergia.expensetracker.domain.User;
import com.coldenergia.expensetracker.repository.UserRepository;
import com.coldenergia.expensetracker.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

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
    public void shouldCreateAdminUserIfThereIsntOne() {
        defaultDataInitializer.insertInitialDataIntoDb();
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(userService).save(userArgumentCaptor.capture());
        User admin = userArgumentCaptor.getValue();
    }

}

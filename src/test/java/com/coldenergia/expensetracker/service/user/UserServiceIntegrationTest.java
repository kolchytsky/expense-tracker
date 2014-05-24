package com.coldenergia.expensetracker.service.user;

import com.coldenergia.expensetracker.builder.UserBuilder;
import com.coldenergia.expensetracker.domain.User;
import com.coldenergia.expensetracker.repository.UserRepository;
import com.coldenergia.expensetracker.service.ServiceIntegrationTest;
import com.coldenergia.expensetracker.service.UserNameIsTakenException;
import com.coldenergia.expensetracker.service.UserService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * User: coldenergia
 * Date: 5/11/14
 * Time: 2:39 PM
 */
public class UserServiceIntegrationTest extends ServiceIntegrationTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void shouldSaveUser() {
        User valid = new UserBuilder().build();
        long initialUserCount = userRepository.count();
        userService.save(valid);
        long finalUserCount = userRepository.count();
        assertEquals(1L, finalUserCount - initialUserCount);
    }

    @Test
    public void shouldSaveUserWithNewPassword() {
        User user = new UserBuilder().build();
        long initialUserCount = userRepository.count();
        userService.saveUserWithNewPassword(user, "d0m1n4t0r");
        long finalUserCount = userRepository.count();
        assertEquals(1L, finalUserCount - initialUserCount);
    }

    @Test
    public void shouldNotSaveUserWithNonUniqueName() {
        User gkublok = new UserBuilder().withName("Gkublok").build();
        userService.save(gkublok);
        User gkublokImpostor = new UserBuilder().withName("Gkublok").build();
        try {
            userService.save(gkublokImpostor);
            fail("Should've thrown an exception when attempting to save a user with a non-unique name");
        } catch (UserNameIsTakenException expected) {}
    }

}

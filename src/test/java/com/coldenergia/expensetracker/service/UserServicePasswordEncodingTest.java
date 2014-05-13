package com.coldenergia.expensetracker.service;

import com.coldenergia.expensetracker.builder.UserBuilder;
import com.coldenergia.expensetracker.domain.User;
import com.coldenergia.expensetracker.repository.UserRepository;
import com.coldenergia.expensetracker.validator.UserValidator;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.Assert.assertEquals;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * User: coldenergia
 * Date: 5/13/14
 * Time: 8:46 PM
 */
public class UserServicePasswordEncodingTest {

    private UserService userService;

    private UserRepository userRepository;

    private PasswordEncoder passwordEncoder;

    @Before
    public void setup() {
        userRepository = mock(UserRepository.class);
        passwordEncoder = new BCryptPasswordEncoder();
        userService = new UserServiceImpl(userRepository, new UserValidator(), passwordEncoder);
    }

    @Test
    public void shouldBcryptEncodeUserPasswordOnSave() {
        User dominator = new UserBuilder().withPassword("d0m1n4t0r").build();
        userService.save(dominator);
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userArgumentCaptor.capture());
        User capturedDominator = userArgumentCaptor.getValue();
        String expectedEncodedPassword = passwordEncoder.encode("d0m1n4t0r");
        assertEquals(expectedEncodedPassword, capturedDominator.getPassword());
    }

}

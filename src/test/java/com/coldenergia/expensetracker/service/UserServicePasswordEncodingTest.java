package com.coldenergia.expensetracker.service;

import com.coldenergia.expensetracker.builder.UserBuilder;
import com.coldenergia.expensetracker.domain.User;
import com.coldenergia.expensetracker.repository.AuthorityRepository;
import com.coldenergia.expensetracker.repository.UserRepository;
import com.coldenergia.expensetracker.validator.UserValidator;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.Assert.assertEquals;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * User: coldenergia
 * Date: 5/13/14
 * Time: 8:46 PM
 */
public class UserServicePasswordEncodingTest {

    private AuthorityRepository authorityRepository;

    private UserService userService;

    private UserRepository userRepository;

    private PasswordEncoder passwordEncoder;

    @Before
    public void setup() {
        authorityRepository = mock(AuthorityRepository.class);
        userRepository = mock(UserRepository.class);
        passwordEncoder = new BCryptPasswordEncoder();
        userService = new UserServiceImpl(authorityRepository, userRepository, new UserValidator(), passwordEncoder);
    }

    @Test
    public void shouldNotBcryptEncodeUserPasswordOnSave() {
        User dominator = new UserBuilder().withPassword("d0m1n4t0r").build();
        userService.save(dominator);
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userArgumentCaptor.capture());
        User capturedDominator = userArgumentCaptor.getValue();
        assertEquals("d0m1n4t0r", capturedDominator.getPassword());
    }

    @Test
    public void shouldBcryptEncodeUserPasswordOnSaveAndEncode() {
        User dominator = new UserBuilder().withPassword("this value doesn't matter").build();
        userService.saveUserWithNewPassword(dominator, "d0m1n4t0r");
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userArgumentCaptor.capture());
        User capturedDominator = userArgumentCaptor.getValue();
        assertTrue(passwordEncoder.matches("d0m1n4t0r", capturedDominator.getPassword()));
    }

}

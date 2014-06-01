package com.coldenergia.expensetracker.service.user;

import com.coldenergia.expensetracker.repository.AuthorityRepository;
import com.coldenergia.expensetracker.repository.UserRepository;
import com.coldenergia.expensetracker.service.UserService;
import com.coldenergia.expensetracker.service.UserServiceImpl;
import com.coldenergia.expensetracker.validator.UserValidator;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mockito.Mockito.mock;

/**
 * User: coldenergia
 * Date: 6/1/14
 * Time: 11:48 AM
 */
public abstract class AbstractUserServiceTest {

    protected AuthorityRepository authorityRepository;

    protected UserService userService;

    protected UserRepository userRepository;

    protected PasswordEncoder passwordEncoder;

    public void setup() {
        authorityRepository = mock(AuthorityRepository.class);
        userRepository = mock(UserRepository.class);
        passwordEncoder = new BCryptPasswordEncoder();
        userService = new UserServiceImpl(authorityRepository, userRepository, new UserValidator(), passwordEncoder);
    }

}

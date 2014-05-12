package com.coldenergia.expensetracker.service;

import com.coldenergia.expensetracker.builder.UserBuilder;
import com.coldenergia.expensetracker.config.JpaConfiguration;
import com.coldenergia.expensetracker.domain.User;
import com.coldenergia.expensetracker.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

/**
 * User: coldenergia
 * Date: 5/11/14
 * Time: 2:39 PM
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { JpaConfiguration.class })
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class UserServiceIntegrationTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void shouldSaveValidUser() {
        User valid = new UserBuilder().build();
        long initialUserCount = userRepository.count();
        userService.save(valid);
        long finalUserCount = userRepository.count();
        assertEquals(1L, finalUserCount - initialUserCount);
    }

}

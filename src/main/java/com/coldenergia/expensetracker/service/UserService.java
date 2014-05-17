package com.coldenergia.expensetracker.service;

import com.coldenergia.expensetracker.domain.User;
import org.springframework.stereotype.Service;

/**
 * User: coldenergia
 * Date: 5/10/14
 * Time: 8:04 PM
 */
public interface UserService {

    User save(User user);

    /**
     * Saves the user and sets a new user password (encoding it in the process).
     * @param user
     * @param rawPassword New raw (plain) password for the user
     * */
    User saveUserWithNewPassword(User user, String rawPassword);

    User findByName(String name);

}

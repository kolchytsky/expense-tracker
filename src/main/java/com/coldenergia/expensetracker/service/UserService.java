package com.coldenergia.expensetracker.service;

import com.coldenergia.expensetracker.domain.User;

import java.util.List;
import java.util.Set;

/**
 * User: coldenergia
 * Date: 5/10/14
 * Time: 8:04 PM
 */
public interface UserService {

    /**
     * @throws UserNameIsTakenException If user name has already been taken by another user.
     * */
    User save(User user);

    /**
     * Saves the user and sets a new user password (encoding it in the process).
     * @param user
     * @param rawPassword New raw (plain) password for the user
     * @throws UserNameIsTakenException If user name has already been taken by another user.
     * */
    User saveUserWithNewPassword(User user, String rawPassword);

    /**
     * Saves the user with authorities as specified by authority names list
     * and sets a new user password (encoding it in the process). The {@link User#authorities} are
     * ignored at this point.
     * @param user
     * @param authorityNames List of authority names matching names of already existing authorities
     * @param rawPassword New raw (plain) password for the user
     * @throws UserNameIsTakenException If user name has already been taken by another user.
     * */
    User saveUserWithNewPassword(User user, Set<String> authorityNames, String rawPassword);

    User findByName(String name);

    List<User> findAll();

    /**
     * @return List of users who have the "spender" authority.
     * */
    List<User> findAllSpenders();

}

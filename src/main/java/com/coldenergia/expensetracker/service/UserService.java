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

    User findByName(String name);

}

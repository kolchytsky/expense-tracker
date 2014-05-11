package com.coldenergia.expensetracker.defaultdata;

import com.coldenergia.expensetracker.domain.User;
import com.coldenergia.expensetracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * User: coldenergia
 * Date: 5/10/14
 * Time: 5:54 PM
 */
// TODO: No @transactional is intentional - I want an integration test for it, perhaps
@Component
@Transactional(readOnly = true)
public class DefaultDataInitializer {

    private final UserService userService;

    @Autowired
    public DefaultDataInitializer(UserService userService) {
        this.userService = userService;
    }

    @Transactional
    public void insertInitialDataIntoDb() {
        User admin = new User();
        admin.setName("admin");
        admin.setPassword("mandible");
        //admin.setAuthorities();
        userService.save(admin);
    }

}

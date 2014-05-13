package com.coldenergia.expensetracker.defaultdata;

import com.coldenergia.expensetracker.domain.Authority;
import com.coldenergia.expensetracker.domain.User;
import com.coldenergia.expensetracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.coldenergia.expensetracker.defaultdata.DefaultDataConstants.DEFAULT_ADMIN_NAME;
import static com.coldenergia.expensetracker.defaultdata.DefaultDataConstants.DEFAULT_ADMIN_PASSWORD;

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
        // TODO: Need proper checks here, to ensure that there are no such authorities / user - uniqueness will be handled by DB constaints then...
        // Create authorities
        Authority adminAuthority = new Authority();
        adminAuthority.setName("ADMIN");

        Authority userAuthority = new Authority();
        userAuthority.setName("USER");

        createDefaultAdminIfThereIsntOne(adminAuthority);
    }

    // TODO: Transactions, transactions, transactions - test for them!
    /**
     * Creates a default administrator user if there isn't one.
     * */
    private void createDefaultAdminIfThereIsntOne(Authority adminAuthority) {
        boolean isDefaultAdminNotCreated = userService.findByName(DEFAULT_ADMIN_NAME) == null;
        if (isDefaultAdminNotCreated) {
            List<Authority> authorityList = new ArrayList<Authority>();
            authorityList.add(adminAuthority);

            User admin = new User();
            admin.setName(DEFAULT_ADMIN_NAME);
            admin.setPassword(DEFAULT_ADMIN_PASSWORD);
            admin.setAuthorities(authorityList);
            userService.save(admin);
        }
    }

}

package com.coldenergia.expensetracker.defaultdata;

import com.coldenergia.expensetracker.domain.Authority;
import com.coldenergia.expensetracker.domain.User;
import com.coldenergia.expensetracker.service.AuthorityService;
import com.coldenergia.expensetracker.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.coldenergia.expensetracker.defaultdata.DefaultDataConstants.*;

/**
 * User: coldenergia
 * Date: 5/10/14
 * Time: 5:54 PM
 */
// TODO: No @transactional is intentional - I want an integration test for it, perhaps
@Component
@Transactional(readOnly = true)
public class DefaultDataInitializer {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultDataInitializer.class);

    private final UserService userService;

    private final AuthorityService authorityService;

    @Autowired
    public DefaultDataInitializer(UserService userService, AuthorityService authorityService) {
        this.userService = userService;
        this.authorityService = authorityService;
    }

    @Transactional
    public void insertInitialDataIntoDb() {
        LOGGER.info("Checking presence of / inserting initial data...");
        createAuthorities();
        createDefaultAdminIfThereIsntOne();
        LOGGER.info("Ensured all necessary default data is present.");
    }

    // TODO: Transactions, transactions, transactions - test for them!
    /**
     * Creates a default administrator user if there isn't one.
     * */
    private void createDefaultAdminIfThereIsntOne() {
        boolean isDefaultAdminNotCreated = userService.findByName(DEFAULT_ADMIN_NAME) == null;
        if (isDefaultAdminNotCreated) {
            List<Authority> authorityList = new ArrayList<Authority>();
            Authority adminAuthority = authorityService.findByName(ADMIN_AUTHORITY_NAME);
            authorityList.add(adminAuthority);

            User admin = new User();
            admin.setName(DEFAULT_ADMIN_NAME);
            admin.setPassword(null);
            admin.setAuthorities(authorityList);
            userService.saveUserWithNewPassword(admin, DEFAULT_ADMIN_PASSWORD);
        }
    }

    private void createAuthorities() {
        String[] authorityNames = { ADMIN_AUTHORITY_NAME, USER_AUTHORITY_NAME };
        for (String authorityName : authorityNames) {
            boolean isAuthorityAbsent = authorityService.findByName(authorityName) == null;
            if (isAuthorityAbsent) {
                Authority authority = new Authority();
                authority.setName(authorityName);
                authorityService.save(authority);
            }
        }
    }

}

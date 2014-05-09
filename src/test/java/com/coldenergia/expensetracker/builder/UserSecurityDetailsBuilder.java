package com.coldenergia.expensetracker.builder;

import com.coldenergia.expensetracker.domain.UserSecurityDetails;

import java.util.Arrays;

/**
 * User: coldenergia
 * Date: 5/9/14
 * Time: 12:31 PM
 */
public class UserSecurityDetailsBuilder {

    private UserSecurityDetails userDetails;

    public UserSecurityDetailsBuilder() {
        userDetails = new UserSecurityDetails();
        userDetails.setUsername("Gkublok");
        userDetails.setPassword(null);
        String[] authorityNames = {
                "ADMIN"
        };
        userDetails.setAuthorities(Arrays.asList(authorityNames));
    }

    public UserSecurityDetails build() {
        return userDetails;
    }

    public UserSecurityDetailsBuilder withUsername(String username) {
        userDetails.setUsername(username);
        return this;
    }

    public UserSecurityDetailsBuilder withPassword(String password) {
        userDetails.setPassword(password);
        return this;
    }

}

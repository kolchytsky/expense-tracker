package com.coldenergia.expensetracker.builder;

import com.coldenergia.expensetracker.domain.Authority;
import com.coldenergia.expensetracker.domain.User;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * User: coldenergia
 * Date: 5/2/14
 * Time: 8:16 PM
 */
public class UserBuilder {

    private User user;

    public UserBuilder() {
        user = new User();
        user.setId(null);
        user.setName("Gkublok");
        Date createdDate;
        try {
            createdDate = new SimpleDateFormat("MM/dd/yyyy").parse("03/16/2004");
        } catch (ParseException e) {
            createdDate = new Date();
        }
        user.setCreated(createdDate);
        Authority[] authorities = {
                new AuthorityBuilder().build()
        };
        user.setAuthorities(Arrays.asList(authorities));
    }

    public User build() {
        return user;
    }

    public UserBuilder withId(Long id) {
        user.setId(id);
        return this;
    }

    public UserBuilder withName(String name) {
        user.setName(name);
        return this;
    }

    public UserBuilder withAuthorities(List<Authority> authorities) {
        user.setAuthorities(authorities);
        return this;
    }

    public UserBuilder withAuthorities(Authority[] authorities) {
        user.setAuthorities(Arrays.asList(authorities));
        return this;
    }

}

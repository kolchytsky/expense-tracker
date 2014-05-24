package com.coldenergia.expensetracker.builder;

import com.coldenergia.expensetracker.domain.Authority;
import com.coldenergia.expensetracker.domain.User;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
        user.setPassword("ssss");
        Date createdDate;
        try {
            createdDate = new SimpleDateFormat("MM/dd/yyyy").parse("03/16/2004");
        } catch (ParseException e) {
            createdDate = new Date();
        }
        user.setCreated(createdDate);
        List<Authority> authorities = new ArrayList<>();
        authorities.add(new AuthorityBuilder().build());
        user.setAuthorities(authorities);
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

    public UserBuilder withPassword(String password) {
        user.setPassword(password);
        return this;
    }

    public UserBuilder withAuthorities(List<Authority> authorities) {
        user.setAuthorities(authorities);
        return this;
    }

    public UserBuilder withAuthorities(Authority[] authorities) {
        List<Authority> authorityList = new ArrayList<>(authorities.length);
        for (Authority a : authorities) {
            authorityList.add(a);
        }
        user.setAuthorities(authorityList);
        return this;
    }

    /**
     * Constructs a user with authority list containing one authority element.
     * @param authority the only authority to comprise the user authority list.
     * */
    public UserBuilder withAuthority(Authority authority) {
        List<Authority> authorities = new ArrayList<>(1);
        authorities.add(authority);
        user.setAuthorities(authorities);
        return this;
    }

    public UserBuilder withCreated(Date created) {
        user.setCreated(created);
        return this;
    }

}

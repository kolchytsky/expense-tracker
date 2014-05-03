package com.coldenergia.expensetracker.builder;

import com.coldenergia.expensetracker.domain.Domain;
import com.coldenergia.expensetracker.domain.User;

import java.util.Arrays;

/**
 * User: coldenergia
 * Date: 5/3/14
 * Time: 3:39 PM
 */
public class DomainBuilder {

    private Domain domain;

    public DomainBuilder() {
        domain = new Domain();
        domain.setName("Rrajigar");
        User[] users = {
                new UserBuilder().withName("Enigma").build(),
                new UserBuilder().withName("Divisor").build()
        };
        domain.setUsers(Arrays.asList(users));
    }

    public Domain build() {
        return domain;
    }

    public DomainBuilder withName(String name) {
        domain.setName(name);
        return this;
    }

    public DomainBuilder withUsers(User[] users) {
        domain.setUsers(Arrays.asList(users));
        return this;
    }

}

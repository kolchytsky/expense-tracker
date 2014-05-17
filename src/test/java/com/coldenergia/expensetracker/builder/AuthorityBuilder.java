package com.coldenergia.expensetracker.builder;

import com.coldenergia.expensetracker.domain.Authority;

import static com.coldenergia.expensetracker.defaultdata.DefaultDataConstants.ADMIN_AUTHORITY_NAME;

/**
 * User: coldenergia
 * Date: 5/2/14
 * Time: 8:27 PM
 */
public class AuthorityBuilder {

    private Authority authority;

    public AuthorityBuilder() {
        authority = new Authority();
        authority.setId(null);
        authority.setName(ADMIN_AUTHORITY_NAME);
    }

    public Authority build() {
        return authority;
    }

    public AuthorityBuilder withId(Integer id) {
        authority.setId(id);
        return this;
    }

    public AuthorityBuilder withName(String name) {
        authority.setName(name);
        return this;
    }

}

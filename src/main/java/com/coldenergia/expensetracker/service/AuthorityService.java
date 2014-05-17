package com.coldenergia.expensetracker.service;

import com.coldenergia.expensetracker.domain.Authority;

/**
 * User: coldenergia
 * Date: 5/12/14
 * Time: 8:32 PM
 */
public interface AuthorityService {

    Authority save(Authority authority);

    Iterable<Authority> save(Iterable<Authority> authorities);

    Authority findByName(String name);

}

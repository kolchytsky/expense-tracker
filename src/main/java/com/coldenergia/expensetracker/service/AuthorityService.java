package com.coldenergia.expensetracker.service;

import com.coldenergia.expensetracker.domain.Authority;

/**
 * User: coldenergia
 * Date: 5/12/14
 * Time: 8:32 PM
 */
public interface AuthorityService {

    Authority save(Authority authority);

    Authority findByName(String name);

}

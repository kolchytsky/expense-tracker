package com.coldenergia.expensetracker.service;

import com.coldenergia.expensetracker.domain.Domain;

import java.util.Set;

/**
 * User: coldenergia
 * Date: 5/24/14
 * Time: 10:11 AM
 */
public interface DomainService {

    Domain save(Domain domain);

    /**
     * Sets domain users. The users must have "spender" authorities.
     * */
    Domain setDomainUsers(Long domainId, Set<Long> userIds);

}

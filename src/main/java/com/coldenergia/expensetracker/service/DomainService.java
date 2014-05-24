package com.coldenergia.expensetracker.service;

import com.coldenergia.expensetracker.domain.Domain;

import java.util.Set;

// TODO: When creating a new domain, a new default ROOT category should be created and associated with this domain
/**
 * User: coldenergia
 * Date: 5/24/14
 * Time: 10:11 AM
 */
public interface DomainService {

    /**
     * @throws DomainNameIsTakenException If the domain name already identifies some other domain.
     * */
    Domain save(Domain domain);

    /**
     * Sets domain users. The users must have "spender" authorities.
     * */
    Domain setDomainUsers(Long domainId, Set<Long> userIds);

}

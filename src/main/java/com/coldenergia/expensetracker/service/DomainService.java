package com.coldenergia.expensetracker.service;

import com.coldenergia.expensetracker.domain.Domain;

import java.util.List;
import java.util.Set;

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

    Domain findOne(Long id);

    /**
     * Finds a domain by its identifier plus initializes (lazy) domain user list.
     * */
    Domain findOneAndInitUserList(Long id);

    List<Domain> findAll();

}

package com.coldenergia.expensetracker.repository;

import com.coldenergia.expensetracker.domain.Domain;
import org.springframework.data.repository.CrudRepository;

/**
 * User: coldenergia
 * Date: 5/3/14
 * Time: 3:36 PM
 */
public interface DomainRepository extends CrudRepository<Domain, Long> {
}

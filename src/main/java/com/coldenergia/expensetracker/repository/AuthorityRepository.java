package com.coldenergia.expensetracker.repository;

import com.coldenergia.expensetracker.domain.Authority;
import org.springframework.data.repository.CrudRepository;

/**
 * User: coldenergia
 * Date: 5/3/14
 * Time: 9:12 PM
 */
public interface AuthorityRepository extends CrudRepository<Authority, Integer> {
}

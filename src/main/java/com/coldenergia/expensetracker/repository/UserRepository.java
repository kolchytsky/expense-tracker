package com.coldenergia.expensetracker.repository;

import com.coldenergia.expensetracker.domain.User;
import org.springframework.data.repository.CrudRepository;

/**
 * User: coldenergia
 * Date: 5/2/14
 * Time: 9:07 PM
 */
public interface UserRepository extends CrudRepository<User, Long> {

    User findByName(String name);

}

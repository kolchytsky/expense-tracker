package com.coldenergia.expensetracker.repository;

import com.coldenergia.expensetracker.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * User: coldenergia
 * Date: 5/2/14
 * Time: 9:07 PM
 */
public interface UserRepository extends CrudRepository<User, Long> {

    User findByName(String name);

    /**
     * @return List of users who have an authority with the specified name.
     * */
    @Query("select u from User u where :authorityName in (select name from u.authorities)")
    List<User> findAllUsersHavingAuthority(@Param("authorityName") String authorityName);

    Page<User> findAll(Pageable pageable);

}

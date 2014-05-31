package com.coldenergia.expensetracker.repository;

import com.coldenergia.expensetracker.domain.Domain;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * User: coldenergia
 * Date: 5/3/14
 * Time: 3:36 PM
 */
public interface DomainRepository extends CrudRepository<Domain, Long> {

    Domain findByName(String name);

    /*
    * Perhaps this query is not as efficient as this native one:
    * @Query(value =
    * "select * from domains join user_domains on
    * domains.id = user_domains.domain_id where user_domains.user_id = :userId", nativeQuery = true),
    * but I'd like to stick to HQL for now.
    * */
    @Query("select d from Domain d where :userId in (select u.id from d.users u)")
    List<Domain> findDomainsAccessibleByUser(@Param("userId") Long userId);

    @Query("select d from Domain d where :name in (select u.name from d.users u)")
    List<Domain> findDomainsAccessibleByUser(@Param("name") String userName);

}

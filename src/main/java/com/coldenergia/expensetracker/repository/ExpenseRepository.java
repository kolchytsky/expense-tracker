package com.coldenergia.expensetracker.repository;

import com.coldenergia.expensetracker.domain.Expense;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 * User: coldenergia
 * Date: 5/3/14
 * Time: 9:19 PM
 */
public interface ExpenseRepository extends CrudRepository<Expense, Long> {

    /**
     * @return An expense having a specified name, which belongs to a category which belongs to a
     * domain with the specified domain indentifier.
     * */
    @Query("select e from Expense e where e.name = :name and e.category.domain.id = :domainId")
    Expense findByNameAndDomainId(@Param("name") String name, @Param("domainId") Long domainId);

}

package com.coldenergia.expensetracker.repository;

import com.coldenergia.expensetracker.domain.Expense;
import com.coldenergia.expensetracker.domain.ExpenseDetail;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

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

    /**
     * Finds a list of distinct {@link Expense}s, which are associated with the {@link ExpenseDetail}s,
     * whose identifiers have been passed as a list.
     * @param expenseDetailsIdList List of {@link ExpenseDetail}s identifiers.
     * */
    @Query("select distinct ed.expense from ExpenseDetail ed where ed.id in (:idList)")
    List<Expense> findExpensesByChildDetailsIdList(@Param("idList") List<Long> expenseDetailsIdList);

    /**
     * @return The count of {@link ExpenseDetail}s, associated with this {@link Expense}.
     * */
    @Query("select count(ed.id) from ExpenseDetail ed where ed.expense = :expense")
    long getExpenseDetailCountForExpense(@Param("expense") Expense expense);

}

package com.coldenergia.expensetracker.repository;

import com.coldenergia.expensetracker.domain.ExpenseDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * User: coldenergia
 * Date: 5/3/14
 * Time: 9:31 PM
 */
public interface ExpenseDetailRepository extends CrudRepository<ExpenseDetail, Long> {

    Page<ExpenseDetail> findAll(Pageable pageable);

    Page<ExpenseDetail> findByExpenseCategoryDomainId(Long domainId, Pageable pageable);

    /* About the @Modifying annotation from docs:
    As this approach is feasible for comprehensive custom functionality,
    you can achieve the execution of modifying queries that actually only
    need parameter binding by annotating the query method with @Modifying:
    As the EntityManager might contain outdated entities after the execution
    of the modifying query, we do not automatically clear it
    (see JavaDoc of EntityManager.clear() for details) since this will effectively
    drop all non-flushed changes still pending in the EntityManager. If you wish the
    EntityManager to be cleared automatically you can set @Modifying annotation's
    clearAutomatically attribute to true.
    * */
    /**
     * Deletes {@link ExpenseDetail}s, whose ids belong to the passed
     * identifier list.
     * @param idList List of {@link ExpenseDetail} identifiers.
     * */
    @Modifying(clearAutomatically = true)
    @Query("delete from ExpenseDetail ed where ed.id in (:idList)")
    void delete(@Param("idList") List<Long> idList);

}

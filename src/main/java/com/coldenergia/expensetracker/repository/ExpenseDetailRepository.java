package com.coldenergia.expensetracker.repository;

import com.coldenergia.expensetracker.domain.Domain;
import com.coldenergia.expensetracker.domain.ExpenseDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

/**
 * User: coldenergia
 * Date: 5/3/14
 * Time: 9:31 PM
 */
public interface ExpenseDetailRepository extends CrudRepository<ExpenseDetail, Long> {

    Page<ExpenseDetail> findAll(Pageable pageable);

    //Page<ExpenseDetail> findByExpenseCategoryDomain(Domain domain, Pageable pageable);
    Page<ExpenseDetail> findByExpenseCategoryDomainId(Long domainId, Pageable pageable);

}

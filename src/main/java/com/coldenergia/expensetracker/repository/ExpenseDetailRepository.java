package com.coldenergia.expensetracker.repository;

import com.coldenergia.expensetracker.domain.ExpenseDetail;
import org.springframework.data.repository.CrudRepository;

/**
 * User: coldenergia
 * Date: 5/3/14
 * Time: 9:31 PM
 */
public interface ExpenseDetailRepository extends CrudRepository<ExpenseDetail, Long> {
}

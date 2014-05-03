package com.coldenergia.expensetracker.repository;

import com.coldenergia.expensetracker.domain.Expense;
import org.springframework.data.repository.CrudRepository;

/**
 * User: coldenergia
 * Date: 5/3/14
 * Time: 9:19 PM
 */
public interface ExpenseRepository extends CrudRepository<Expense, Long> {
}

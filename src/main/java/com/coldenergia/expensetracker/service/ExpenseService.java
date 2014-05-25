package com.coldenergia.expensetracker.service;

import com.coldenergia.expensetracker.domain.Expense;
import com.coldenergia.expensetracker.domain.ExpenseDetail;

/**
 * User: coldenergia
 * Date: 5/25/14
 * Time: 4:59 PM
 */
public interface ExpenseService {

    Expense save(Expense expense);

    ExpenseDetail save(ExpenseDetail expenseDetail);

}

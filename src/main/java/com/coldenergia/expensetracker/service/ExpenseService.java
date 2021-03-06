package com.coldenergia.expensetracker.service;

import com.coldenergia.expensetracker.domain.Expense;
import com.coldenergia.expensetracker.domain.ExpenseDetail;
import com.coldenergia.expensetracker.domain.NamedExpenseDetailHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * User: coldenergia
 * Date: 5/25/14
 * Time: 4:59 PM
 */
public interface ExpenseService {

    Expense save(Expense expense);

    ExpenseDetail save(ExpenseDetail expenseDetail);

    /**
     * This method is the primary method which is meant to log expenses.
     * A new {@link ExpenseDetail} will be created.
     * If there's no {@link Expense} with the same name as {@code expenseName}
     * for that domain, then a new {@link Expense} will be created as well.<br>
     * Also, the newly created {@link Expense}s will belong to the root
     * {@link com.coldenergia.expensetracker.domain.Category} for that domain.
     * */
    ExpenseDetail logExpense(ExpenseDetail expenseDetail, String expenseName, Long domainId);

    /**
     * Logs expenses in batches. The contract is the same as for the
     * {@link ExpenseService#logExpense(com.coldenergia.expensetracker.domain.ExpenseDetail, String, Long)}
     * method.
     * */
    List<ExpenseDetail> logExpenses(List<NamedExpenseDetailHolder> expenses, Long domainId);

    Page<ExpenseDetail> findExpensesByDomainId(Long domainId, Pageable pageable);

    /**
     * Deletes {@link ExpenseDetail}s, whose identifiers are present in the id list.
     * If some {@link Expense}s previously associated with the deleted details don't have
     * any more details associated with them, these {@link Expense}s are deleted as well.
     * @param expenseDetailsIdList List of {@link ExpenseDetail}s identifiers.
     * */
    void deleteExpensesByChildDetailsIdList(List<Long> expenseDetailsIdList);

}

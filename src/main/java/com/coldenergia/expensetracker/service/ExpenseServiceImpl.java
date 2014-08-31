package com.coldenergia.expensetracker.service;

import com.coldenergia.expensetracker.domain.Expense;
import com.coldenergia.expensetracker.domain.ExpenseDetail;
import com.coldenergia.expensetracker.domain.NamedExpenseDetailHolder;
import com.coldenergia.expensetracker.repository.CategoryRepository;
import com.coldenergia.expensetracker.repository.ExpenseDetailRepository;
import com.coldenergia.expensetracker.repository.ExpenseRepository;
import com.coldenergia.expensetracker.validator.ExpenseDetailValidator;
import com.coldenergia.expensetracker.validator.ExpenseValidator;
import com.coldenergia.expensetracker.validator.ValidationResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * User: coldenergia
 * Date: 5/25/14
 * Time: 4:59 PM
 */
@Service
@Transactional(readOnly = true)
public class ExpenseServiceImpl implements ExpenseService {

    private final ExpenseRepository expenseRepository;

    private final ExpenseDetailRepository expenseDetailRepository;

    private final CategoryRepository categoryRepository;

    private final ExpenseValidator expenseValidator;

    private final ExpenseDetailValidator expenseDetailValidator;

    @Autowired
    public ExpenseServiceImpl(
            ExpenseRepository expenseRepository,
            ExpenseDetailRepository expenseDetailRepository,
            CategoryRepository categoryRepository,
            ExpenseValidator expenseValidator,
            ExpenseDetailValidator expenseDetailValidator) {
        this.expenseRepository = expenseRepository;
        this.expenseDetailRepository = expenseDetailRepository;
        this.categoryRepository = categoryRepository;
        this.expenseValidator = expenseValidator;
        this.expenseDetailValidator = expenseDetailValidator;
    }

    @Transactional
    @Override
    public Expense save(Expense expense) {
        validate(expense);
        return expenseRepository.save(expense);
    }

    @Transactional
    @Override
    public ExpenseDetail save(ExpenseDetail expenseDetail) {
        validate(expenseDetail);
        return expenseDetailRepository.save(expenseDetail);
    }

    @Transactional
    @Override
    public ExpenseDetail logExpense(ExpenseDetail expenseDetail, String expenseName, Long domainId) {
        Expense existingExpense = expenseRepository.findByNameAndDomainId(expenseName, domainId);
        if (existingExpense != null) {
            expenseDetail.setExpense(existingExpense);
        } else {
            Expense expense = new Expense();
            expense.setName(expenseName);
            expense.setCategory(categoryRepository.getDomainRootCategory(domainId));
            save(expense);

            expenseDetail.setExpense(expense);
        }

        save(expenseDetail);
        return expenseDetail;
    }

    @Transactional
    @Override
    public List<ExpenseDetail> logExpenses(List<NamedExpenseDetailHolder> expenses, Long domainId) {
        List<ExpenseDetail> results = new ArrayList<>(expenses.size());
        for (NamedExpenseDetailHolder expenseHolder : expenses) {
            ExpenseDetail result = logExpense(
                    expenseHolder.getExpenseDetail(),
                    expenseHolder.getExpenseName(),
                    domainId
            );
            results.add(result);
        }
        return results;
    }

    @Override
    public Page<ExpenseDetail> findExpensesByDomainId(Long domainId, Pageable pageable) {
        return expenseDetailRepository.findByExpenseCategoryDomainId(domainId, pageable);
    }

    @Transactional
    @Override
    public void deleteExpensesByChildDetailsIdList(List<Long> expenseDetailsIdList) {
        List<Expense> expenses = expenseRepository.findExpensesByChildDetailsIdList(expenseDetailsIdList);
        expenseDetailRepository.delete(expenseDetailsIdList);
        for (Expense expense : expenses) {
            long detailCount = expenseRepository.getExpenseDetailCountForExpense(expense);
            boolean doesntHaveAnyDetailsLeft = detailCount == 0;
            if (doesntHaveAnyDetailsLeft) {
                expenseRepository.delete(expense);
            }
        }
    }

    private void validate(Expense expense) {
        ValidationResult result = expenseValidator.validate(expense);
        if (result.hasErrors()) {
            throw new ServiceException(result.getAggregatedErrorCodes());
        }
    }

    private void validate(ExpenseDetail expenseDetail) {
        ValidationResult result = expenseDetailValidator.validate(expenseDetail);
        if (result.hasErrors()) {
            throw new ServiceException(result.getAggregatedErrorCodes());
        }
    }

}

package com.coldenergia.expensetracker.service;

import com.coldenergia.expensetracker.domain.Expense;
import com.coldenergia.expensetracker.repository.ExpenseDetailRepository;
import com.coldenergia.expensetracker.repository.ExpenseRepository;
import com.coldenergia.expensetracker.validator.ExpenseValidator;
import com.coldenergia.expensetracker.validator.ValidationResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    private final ExpenseValidator expenseValidator;

    @Autowired
    public ExpenseServiceImpl(
            ExpenseRepository expenseRepository,
            ExpenseDetailRepository expenseDetailRepository,
            ExpenseValidator expenseValidator) {
        this.expenseRepository = expenseRepository;
        this.expenseDetailRepository = expenseDetailRepository;
        this.expenseValidator = expenseValidator;
    }

    @Override
    public Expense save(Expense expense) {
        validate(expense);
        return expenseRepository.save(expense);
    }

    private void validate(Expense expense) {
        ValidationResult result = expenseValidator.validate(expense);
        if (result.hasErrors()) {
            throw new ServiceException(result.getAggregatedErrorCodes());
        }
    }

}

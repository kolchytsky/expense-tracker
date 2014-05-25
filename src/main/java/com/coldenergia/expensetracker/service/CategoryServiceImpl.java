package com.coldenergia.expensetracker.service;

import com.coldenergia.expensetracker.domain.Category;
import com.coldenergia.expensetracker.repository.CategoryRepository;
import com.coldenergia.expensetracker.validator.CategoryValidator;
import com.coldenergia.expensetracker.validator.ValidationResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * User: coldenergia
 * Date: 5/25/14
 * Time: 12:37 PM
 */
@Service
@Transactional(readOnly = true)
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    private final CategoryValidator categoryValidator;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository, CategoryValidator categoryValidator) {
        this.categoryRepository = categoryRepository;
        this.categoryValidator = categoryValidator;
    }

    @Transactional
    @Override
    public Category save(Category category) {
        validate(category);
        return categoryRepository.save(category);
    }

    @Override
    public Category findOne(Long id) {
        return categoryRepository.findOne(id);
    }

    private void validate(Category category) {
        ValidationResult result = categoryValidator.validate(category);
        if (result.hasErrors()) {
            throw new ServiceException(result.getAggregatedErrorCodes());
        }
    }

}

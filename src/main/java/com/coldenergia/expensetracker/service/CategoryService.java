package com.coldenergia.expensetracker.service;

import com.coldenergia.expensetracker.domain.Category;

/**
 * User: coldenergia
 * Date: 5/25/14
 * Time: 12:34 PM
 */
public interface CategoryService {

    Category save(Category category);

    Category findOne(Long id);

}

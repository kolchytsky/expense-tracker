package com.coldenergia.expensetracker.repository;

import com.coldenergia.expensetracker.domain.Category;
import org.springframework.data.repository.CrudRepository;

/**
 * User: coldenergia
 * Date: 5/3/14
 * Time: 4:54 PM
 */
public interface CategoryRepository extends CrudRepository<Category, Long> {
}

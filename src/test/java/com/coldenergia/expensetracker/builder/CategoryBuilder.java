package com.coldenergia.expensetracker.builder;

import com.coldenergia.expensetracker.domain.Category;
import com.coldenergia.expensetracker.domain.Domain;

/**
 * User: coldenergia
 * Date: 5/3/14
 * Time: 4:40 PM
 */
public class CategoryBuilder {

    private Category category;

    public CategoryBuilder() {
        category = new Category();
        category.setName("integrated weapon systems");
        category.setDomain(null);
        category.setChildCategories(null);
        category.setParentCategory(null);
    }

    public Category build() {
        return category;
    }

    public CategoryBuilder withName(String name) {
        category.setName(name);
        return this;
    }

    public CategoryBuilder withDomain(Domain domain) {
        category.setDomain(domain);
        return this;
    }

    public CategoryBuilder withParentCategory(Category parentCategory) {
        category.setParentCategory(parentCategory);
        return this;
    }

}

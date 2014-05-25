package com.coldenergia.expensetracker.service.category;

import com.coldenergia.expensetracker.builder.CategoryBuilder;
import com.coldenergia.expensetracker.domain.Category;
import com.coldenergia.expensetracker.repository.CategoryRepository;
import com.coldenergia.expensetracker.service.CategoryService;
import com.coldenergia.expensetracker.service.ServiceIntegrationTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.coldenergia.expensetracker.internal.test.data.TestDataInitializer.ACATANA;
import static com.coldenergia.expensetracker.internal.test.data.TestDataInitializer.domains;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * User: coldenergia
 * Date: 5/25/14
 * Time: 1:18 PM
 */
public class CategoryServiceIntegrationTest extends ServiceIntegrationTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryService categoryService;

    @Test
    public void shouldSaveCategory() {
        Category category = new CategoryBuilder().withDomain(domains(ACATANA)).build();
        long initialCount = categoryRepository.count();
        categoryService.save(category);
        long finalCount = categoryRepository.count();
        assertEquals(1L, finalCount - initialCount);
        assertNotNull(category.getId());
    }

}

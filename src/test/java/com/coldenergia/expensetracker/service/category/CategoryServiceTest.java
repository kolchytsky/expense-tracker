package com.coldenergia.expensetracker.service.category;

import com.coldenergia.expensetracker.builder.CategoryBuilder;
import com.coldenergia.expensetracker.domain.Category;
import com.coldenergia.expensetracker.repository.CategoryRepository;
import com.coldenergia.expensetracker.service.CategoryService;
import com.coldenergia.expensetracker.service.CategoryServiceImpl;
import com.coldenergia.expensetracker.service.ServiceException;
import com.coldenergia.expensetracker.validator.CategoryValidator;
import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * User: coldenergia
 * Date: 5/25/14
 * Time: 12:32 PM
 */
public class CategoryServiceTest {

    private CategoryRepository categoryRepository;

    private CategoryService categoryService;

    @Before
    public void setup() {
        this.categoryRepository = mock(CategoryRepository.class);
        this.categoryService = new CategoryServiceImpl(categoryRepository, new CategoryValidator());
    }

    @Test
    public void shouldSaveCategory() {
        Category category = new CategoryBuilder().build();
        categoryService.save(category);
        verify(categoryRepository).save(category);
    }

    @Test
    public void shouldFindCategoryById() {
        Category category = new CategoryBuilder().withId(4L).build();
        when(categoryRepository.findOne(4L)).thenReturn(category);
        Category result = categoryService.findOne(4L);
        assertNotNull(result);
        assertEquals(4L, (long) result.getId());
    }

    @Test
    public void shouldNotSaveCategoryWithEmptyName() {
        Category invalid = new CategoryBuilder().withName("").build();
        assertExceptionOnSave(invalid, "category.name.empty");

        invalid = new CategoryBuilder().withName(null).build();
        assertExceptionOnSave(invalid, "category.name.empty");
    }

    @Test
    public void shouldNotSaveCategoryWithNameExceedingSixtyChars() {
        String invalidName = StringUtils.repeat("a", 61);
        Category invalid = new CategoryBuilder().withName(invalidName).build();
        assertExceptionOnSave(invalid, "category.name.too.long");
    }

    @Test
    public void shouldSaveCategoryWithNameNotExceedingSixtyChars() {
        String validName = StringUtils.repeat("a", 60);
        Category valid = new CategoryBuilder().withName(validName).build();
        categoryService.save(valid);
    }

    @Test
    public void shouldNotSaveCategoryWhichDoesntBelongToDomain() {
        Category category = new CategoryBuilder().withDomain(null).build();
        try {
            categoryService.save(category);
            fail("Should've thrown an exception when attempting to save a category which doesn't belong to a domain");
        } catch (ServiceException expected) {
            assertTrue(expected.getMessage().contains("category.domain.null"));
        }
    }

    private void assertExceptionOnSave(Category invalid, String errorCode) {
        try {
            categoryService.save(invalid);
            fail("Should've thrown an exception here");
        } catch (ServiceException expected) {
            assertNotNull(expected.getMessage());
            assertTrue(expected.getMessage().contains(errorCode));
        }
    }

}

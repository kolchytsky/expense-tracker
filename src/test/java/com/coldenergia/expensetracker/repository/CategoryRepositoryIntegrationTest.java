package com.coldenergia.expensetracker.repository;

import com.coldenergia.expensetracker.builder.CategoryBuilder;
import com.coldenergia.expensetracker.config.JpaConfiguration;
import com.coldenergia.expensetracker.domain.Category;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * User: coldenergia
 * Date: 5/3/14
 * Time: 4:38 PM
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {JpaConfiguration.class})
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class CategoryRepositoryIntegrationTest {

    @Autowired
    private CategoryRepository categoryRepository;

    private Category weapons, rifles;

    @Before
    public void setUp() {
        rifles = new CategoryBuilder().withName("rifles").build();
        weapons = new CategoryBuilder().withName("weapons").build();
    }

    @Test
    public void shouldSaveCategory() {
        Category category = new CategoryBuilder().build();
        Category retrievedCategory = categoryRepository.save(category);
        assertNotNull(retrievedCategory);
        assertEquals(category, retrievedCategory);
    }

    /*
    * Read the spec at least....It says you have to update BOTH sides
    * Note that it is the application that bears responsibility for maintaining the consistency of run-time
    * relationships — for example, for insuring that the “one” and the “many” sides of a bidirectional
    * relationship are consistent with one another when the application updates the relationship at runtime.
    * Here's an excellent explanation:
    * http://stackoverflow.com/questions/3393515/jpa-how-to-have-one-to-many-relation-of-the-same-entity-type
    * */
    /**
     * Brings our attention to the fact that no CASCADE behaviour has been defined.
     * That is why it is needed to take care of everything manually. CASCADEs need to
     * be introduced only when necessary and test-first.
     * */
    @Test
    public void shouldManuallySaveParentCategoryWhenSavingItsChildCategory() {
        rifles.setParentCategory(weapons);
        Category[] childCategories = { rifles };
        weapons.setChildCategories(Arrays.asList(childCategories));
        // Neither parent nor child category have been persisted
        assertNull(rifles.getId());
        assertNull(weapons.getId());
        // According to JPA spec, both ends of a bidirectional relationship must be persisted
        Category retrievedRifles = categoryRepository.save(rifles);
        Category retrievedWeapons = categoryRepository.save(weapons);
        // After having been persisted, our categories are assigned ids
        assertNotNull(retrievedRifles.getId());
        assertNotNull(retrievedWeapons.getId());
        assertEquals(rifles, retrievedRifles);
        assertEquals(weapons, retrievedWeapons);
        assertEquals(weapons, retrievedRifles.getParentCategory());
        assertEquals(rifles, retrievedWeapons.getChildCategories().get(0));
    }

}

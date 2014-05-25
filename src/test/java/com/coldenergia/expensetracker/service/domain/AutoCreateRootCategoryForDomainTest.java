package com.coldenergia.expensetracker.service.domain;

import com.coldenergia.expensetracker.builder.DomainBuilder;
import com.coldenergia.expensetracker.domain.Category;
import com.coldenergia.expensetracker.domain.Domain;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import static com.coldenergia.expensetracker.defaultdata.DefaultDataConstants.ROOT_CATEGORY_NAME;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

/**
 * User: coldenergia
 * Date: 5/25/14
 * Time: 3:45 PM
 */
public class AutoCreateRootCategoryForDomainTest extends AbstractDomainServiceTest {

    @Before
    public void setup() {
        super.setup();
    }

    @Test
    public void shouldCreateRootCategoryForNewDomain() {
        Domain domain = new DomainBuilder().build();

        domainService.save(domain);

        ArgumentCaptor<Category> categoryArgumentCaptor = ArgumentCaptor.forClass(Category.class);
        verify(categoryRepository).save(categoryArgumentCaptor.capture());
        Category category = categoryArgumentCaptor.getValue();
        assertEquals(ROOT_CATEGORY_NAME, category.getName());
    }

    @Test
    public void shouldNotAttemptToCreateRootCategoryForExistingDomain() {
        Domain existingDomain = new DomainBuilder().withId(4L).build();
        when(domainRepository.exists(4L)).thenReturn(true);

        domainService.save(existingDomain);

        verify(categoryRepository, never()).save(any(Category.class));
    }

}

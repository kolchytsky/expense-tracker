package com.coldenergia.expensetracker.web.controller.spender;

import com.coldenergia.expensetracker.builder.DomainBuilder;
import com.coldenergia.expensetracker.domain.Domain;
import com.coldenergia.expensetracker.service.DomainService;
import com.coldenergia.expensetracker.service.ExpenseService;
import com.coldenergia.expensetracker.web.controller.ControllerTest;
import com.coldenergia.expensetracker.web.view.model.expense.ExpensesForm;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ContextConfiguration;

import static com.coldenergia.expensetracker.web.util.SecurityRequestPostProcessors.userDetailsService;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * User: coldenergia
 * Date: 5/31/14
 * Time: 1:06 PM
 */
@ContextConfiguration(classes = { ExpenseLogControllerTest.TestConfiguration.class })
public class ExpenseLogControllerTest extends ControllerTest {

    @Configuration
    public static class TestConfiguration {

        @Bean
        public DomainService domainService() {
            return mock(DomainService.class);
        }

        @Bean
        public ExpenseService expenseService() {
            return mock(ExpenseService.class);
        }

    }

    @Autowired
    private DomainService domainService;

    @Autowired
    private ExpenseService expenseService;

    private Domain targetDomain;

    @Before
    public void setup() {
        super.setup();
        targetDomain = new DomainBuilder().withId(4L).build();
        when(domainService.findOneAccessibleByUser(targetDomain.getId(), THORAX)).thenReturn(targetDomain);
    }

    @Test
    public void shouldListExpensesForSpender() throws Exception {
        when(expenseService.findExpensesByDomainId(eq(targetDomain.getId()), any(Pageable.class)))
                .thenReturn(mock(Page.class));

        mockMvc.perform(get("/domains/" + targetDomain.getId() + "/expenses").with(userDetailsService(THORAX)))
                .andExpect(view().name("spender/expenses/list-expenses"))
                .andExpect(model().attribute("expenses", notNullValue()))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldShowExpensesFormForSpender() throws Exception {
        mockMvc.perform(get("/domains/" + targetDomain.getId() + "/expenses/new").with(userDetailsService(THORAX)))
                .andExpect(view().name("spender/expenses/new-expenses"))
                .andExpect(model().attribute("expensesForm", notNullValue()))
                .andExpect(model().attribute("expensesForm", is(ExpensesForm.class)))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldNotShowExpensesFormIfSpenderDoesntHaveAccessToDomain() throws Exception {
        Domain inaccessible = new DomainBuilder().withId(5L).build();
        when(domainService.findOneAccessibleByUser(inaccessible.getId(), THORAX)).thenReturn(null);

        mockMvc.perform(get("/domains/" + inaccessible.getId() + "/expenses/new").with(userDetailsService(THORAX)))
                .andExpect(status().isForbidden());
    }

    @Test
    public void shouldProcessExpensesForm() throws Exception {
        // TODO: Add actual form stuff
        /*mockMvc.perform(addCsrfToken(
                post("/domains/" + targetDomain.getId() + "/expenses")
                        .with(userDetailsService(THORAX))))
                .andExpect(status().isMovedTemporarily())
                .andExpect(redirectedUrl("/domains/" + targetDomain.getId()));*/
    }

    @Test
    public void shouldNotProcessExpensesFormIfSpenderDoesntHaveAccessToDomain() throws Exception {
        Domain inaccessible = new DomainBuilder().withId(5L).build();
        when(domainService.findOneAccessibleByUser(inaccessible.getId(), THORAX)).thenReturn(null);

        mockMvc.perform(addCsrfToken(
                post("/domains/" + inaccessible.getId() + "/expenses")
                        .with(userDetailsService(THORAX))))
                .andExpect(status().isForbidden());
    }

}

package com.coldenergia.expensetracker.internal.test.data;

import com.coldenergia.expensetracker.builder.CategoryBuilder;
import com.coldenergia.expensetracker.builder.DomainBuilder;
import com.coldenergia.expensetracker.builder.ExpenseBuilder;
import com.coldenergia.expensetracker.domain.Category;
import com.coldenergia.expensetracker.domain.Domain;
import com.coldenergia.expensetracker.domain.Expense;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import java.util.HashMap;
import java.util.Map;

import static com.coldenergia.expensetracker.defaultdata.DefaultDataConstants.ROOT_CATEGORY_NAME;

/**
 * User: coldenergia
 * Date: 5/25/14
 * Time: 2:03 PM
 */
@Component
public class TestDataInitializer {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestDataInitializer.class);

    @Autowired
    private EntityManager entityManager;

    /**
     * A test domain name.
     * */
    public static final String ACATANA = "Acatana";

    private static Map<String, Domain> DOMAINS = new HashMap<>();

    /**
     * A test category name.
     * */
    public static final String MILITARY_RESEARCH = "Military Research";

    private static Map<String, Category> CATEGORIES = new HashMap<>();

    /**
     * A test expense name.
     * */
    public static final String SHOCK_RIFLE = "Shock Rifle model 4";

    private static Map<String, Expense> EXPENSES = new HashMap<>();

    /*
    * As for transactions, I am still unsure about Spring Test Context:
    * Since the EntityManager runs outside the container,
    * the transactions can be managed only by the unit tests.
    * Declarative transactions are not available in this case.
    * This further simplifies the testing, because the transaction boundary
    * can be explicitly set inside a test method.
    * */
    public void insertTestDataIntoDb() {
        LOGGER.info("Started inserting data for integration tests...");
        entityManager.getTransaction().begin();
        createDomains();
        createCategories();
        createExpenses();
        entityManager.getTransaction().commit();
        LOGGER.info("Finished inserting data for integration tests...");
    }

    private void createDomains() {
        // We may run into a problem with this - but then we'll issue a check against the database
        if (domains(ACATANA) == null) {
            Domain acatana = new DomainBuilder().withName(ACATANA).withNoUsers().build();
            entityManager.persist(acatana);
            DOMAINS.put(ACATANA, acatana);
        }
    }

    public static Domain domains(String domainName) {
        return DOMAINS.get(domainName);
    }

    private void createCategories() {
        if (categories(MILITARY_RESEARCH) == null) {
            // Won't be putting Acatana root category into categories map
            Category rootForAcatana = new CategoryBuilder()
                    .withName(ROOT_CATEGORY_NAME)
                    .withDomain(domains(ACATANA))
                    .build();
            entityManager.persist(rootForAcatana);

            Category militaryResearch = new CategoryBuilder()
                    .withName(MILITARY_RESEARCH)
                    .withDomain(domains(ACATANA))
                    .withParentCategory(rootForAcatana)
                    .build();
            entityManager.persist(militaryResearch);
            CATEGORIES.put(MILITARY_RESEARCH, militaryResearch);
        }
    }

    public static Category categories(String categoryName) {
        return CATEGORIES.get(categoryName);
    }

    private void createExpenses() {
        if (expenses(SHOCK_RIFLE) == null) {
            Expense shockRifle = new ExpenseBuilder()
                    .withName(SHOCK_RIFLE)
                    .withCategory(categories(MILITARY_RESEARCH))
                    .build();
            entityManager.persist(shockRifle);
            EXPENSES.put(SHOCK_RIFLE, shockRifle);
        }
    }

    public static Expense expenses(String expenseName) {
        return EXPENSES.get(expenseName);
    }

}

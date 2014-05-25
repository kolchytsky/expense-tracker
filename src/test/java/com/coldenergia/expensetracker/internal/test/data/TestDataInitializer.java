package com.coldenergia.expensetracker.internal.test.data;

import com.coldenergia.expensetracker.builder.DomainBuilder;
import com.coldenergia.expensetracker.domain.Domain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import java.util.HashMap;
import java.util.Map;

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

    public static final String ACATANA = "Acatana";

    private static Map<String, Domain> DOMAINS = new HashMap<>();

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
        entityManager.getTransaction().commit();
        LOGGER.info("Finished inserting data for integration tests...");
    }

    private void createDomains() {
        // We may run into a problem with this - but then we'll issue a check against the database
        if (domains(ACATANA) == null) {
            Domain acatana = new DomainBuilder().withName("Acatana").withNoUsers().build();
            entityManager.persist(acatana);
            DOMAINS.put(ACATANA, acatana);
        }
    }

    public static Domain domains(String domainName) {
        return DOMAINS.get(domainName);
    }

}

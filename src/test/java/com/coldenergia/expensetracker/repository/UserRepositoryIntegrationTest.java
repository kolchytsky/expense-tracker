package com.coldenergia.expensetracker.repository;

import com.coldenergia.expensetracker.builder.UserBuilder;
import com.coldenergia.expensetracker.config.JpaConfiguration;
import com.coldenergia.expensetracker.domain.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

/**
 * User: coldenergia
 * Date: 5/2/14
 * Time: 9:04 PM
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {JpaConfiguration.class})
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class UserRepositoryIntegrationTest {

    @Autowired
    private UserRepository userRepository;

    private User kraagesh;

    @Before
    public void setUp() {
        kraagesh = new UserBuilder().withName("Kraagesh").build();
        userRepository.save(kraagesh);
    }

    /* Avoid false positives when testing ORM code
    * When you test application code that manipulates the state of the Hibernate session,
    * make sure to flush the underlying session within test methods that execute that code.
    * Failing to flush the underlying session can produce false positives:
    * your test may pass, but the same code throws an exception in a live, production environment.
    * In the following Hibernate-based example test case, one method demonstrates a false positive,
    * and the other method correctly exposes the results of flushing the session.
    * @Autowired
    * private SessionFactory sessionFactory;
    *
    * @Test // no expected exception!
    * public void falsePositive() {
    * updateEntityInHibernateSession();
    * // False positive: an exception will be thrown once the session is
    * // finally flushed (i.e., in production code)
    * }
    * @Test(expected = GenericJDBCException.class)
    * public void updateWithSessionFlush() {
    * updateEntityInHibernateSession();
    * // Manual flush is required to avoid false positive in test
    * sessionFactory.getCurrentSession().flush();
    * }
    * Note that this applies to JPA and any other ORM frameworks that maintain an in-memory unit of work.
     */
    @Test
    public void shouldSaveUser() {
        User user = new UserBuilder().build();
        User retrievedUser = userRepository.save(user);
        assertNotNull(retrievedUser);
        assertEquals(user, retrievedUser);
    }

    @Test
    public void shouldFindUserByName() {
        User retrievedUser = userRepository.findByName("Kraagesh");
        assertNotNull(retrievedUser);
        assertEquals(kraagesh, retrievedUser);
    }

    @Test
    public void shouldReturnNullIfUserNotFoundByName() {
        User mandible = userRepository.findByName("Mandible");
        assertNull(mandible);
    }

}

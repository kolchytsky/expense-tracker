package com.coldenergia.expensetracker.repository;

import com.coldenergia.expensetracker.builder.AuthorityBuilder;
import com.coldenergia.expensetracker.builder.UserBuilder;
import com.coldenergia.expensetracker.domain.Authority;
import com.coldenergia.expensetracker.domain.User;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.*;

/**
 * User: coldenergia
 * Date: 5/2/14
 * Time: 9:04 PM
 */
public class UserRepositoryIntegrationTest extends RepositoryIntegrationTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthorityRepository authorityRepository;

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
        User kraagesh = new UserBuilder().withName("Kraagesh").build();
        userRepository.save(kraagesh);
        User retrievedUser = userRepository.findByName("Kraagesh");
        assertNotNull(retrievedUser);
        assertEquals(kraagesh, retrievedUser);
    }

    @Test
    public void shouldReturnNullIfUserNotFoundByName() {
        User mandible = userRepository.findByName("Mandible");
        assertNull(mandible);
    }

    @Test
    public void shouldFindUsersHavingAuthority() {
        Authority guardian = new AuthorityBuilder().withName("guardian").build();
        Authority destroyer = new AuthorityBuilder().withName("destroyer").build();

        authorityRepository.save(guardian);
        authorityRepository.save(destroyer);

        User kraagesh = new UserBuilder().withName("Kraagesh").withAuthority(guardian).build();
        User dominator = new UserBuilder().withName("Dominator").withAuthority(destroyer).build();

        userRepository.save(kraagesh);
        userRepository.save(dominator);

        List<User> users = userRepository.findAllUsersHavingAuthority("destroyer");
        assertTrue(users.size() == 1);
        assertEquals("Dominator", users.get(0).getName());
    }

}

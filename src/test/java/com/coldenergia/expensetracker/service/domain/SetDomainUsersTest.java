package com.coldenergia.expensetracker.service.domain;

import com.coldenergia.expensetracker.builder.AuthorityBuilder;
import com.coldenergia.expensetracker.builder.DomainBuilder;
import com.coldenergia.expensetracker.builder.UserBuilder;
import com.coldenergia.expensetracker.domain.Domain;
import com.coldenergia.expensetracker.domain.User;
import com.coldenergia.expensetracker.repository.DomainRepository;
import com.coldenergia.expensetracker.repository.UserRepository;
import com.coldenergia.expensetracker.service.DomainService;
import com.coldenergia.expensetracker.service.DomainServiceImpl;
import com.coldenergia.expensetracker.service.ServiceException;
import com.coldenergia.expensetracker.validator.DomainValidator;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.coldenergia.expensetracker.defaultdata.DefaultDataConstants.SPENDER_AUTHORITY_NAME;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * User: coldenergia
 * Date: 5/24/14
 * Time: 1:25 PM
 */
public class SetDomainUsersTest extends AbstractDomainServiceTest {

    private User gkublokAdmin;

    private User thoraxSpender;

    private User cobaltSpender;

    private Domain acatana;

    @Before
    public void setup() {
        super.setup();

        thoraxSpender = new UserBuilder().withId(7L).withAuthority(AuthorityBuilder.SPENDER_AUTHORITY).build();
        cobaltSpender = new UserBuilder().withId(1010L).withAuthority(AuthorityBuilder.SPENDER_AUTHORITY).build();
        gkublokAdmin = new UserBuilder().withId(2005L).withAuthority(AuthorityBuilder.ADMIN_AUTHORITY).build();

        User[] existingDomainUsers = {
                new UserBuilder().build(),
                new UserBuilder().build(),
                new UserBuilder().build()
        };
        acatana = new DomainBuilder().withId(4L).withUsers(existingDomainUsers).build();

        when(domainRepository.findOne(4L)).thenReturn(acatana);

        returnTwoSpenders();
        returnSpenderAndAdmin();
    }

    @Test
    public void shouldSetDomainUsers() {
        Set<Long> userIds = new HashSet<>(2);
        userIds.add(thoraxSpender.getId());
        userIds.add(cobaltSpender.getId());

        domainService.setDomainUsers(acatana.getId(), userIds);

        verify(domainRepository).findOne(acatana.getId());

        ArgumentCaptor<Domain> domainArgumentCaptor = ArgumentCaptor.forClass(Domain.class);
        verify(domainRepository).save(domainArgumentCaptor.capture());

        Domain target = domainArgumentCaptor.getValue();
        assertNotNull(target);
        assertTrue(target.getId() == acatana.getId());

        assertTrue(target.getUsers().size() == 2);
        for (User u : target.getUsers()) {
            assertTrue(u.getId() == thoraxSpender.getId() || u.getId() == cobaltSpender.getId());
        }
    }

    @Test
    public void shouldNotSetDomainUsersForNonExistingDomain() {
        when(domainRepository.findOne(5L)).thenReturn(null);
        try {
            domainService.setDomainUsers(5L, new HashSet<Long>());
            fail("Should've thrown an exception here.");
        } catch (Exception expected) {}
    }

    @Test
    public void shouldRemoveExistingDomainUsersIfSetDomainUsersCalledWithEmptyUserIdSet() {
        assertFalse(acatana.getUsers().isEmpty());
        Set<Long> emptyUserIdSet = new HashSet<>();

        domainService.setDomainUsers(acatana.getId(), emptyUserIdSet);

        ArgumentCaptor<Domain> domainArgumentCaptor = ArgumentCaptor.forClass(Domain.class);
        verify(domainRepository).save(domainArgumentCaptor.capture());

        Domain target = domainArgumentCaptor.getValue();
        assertNotNull(target);
        assertTrue(target.getUsers().isEmpty());
    }

    @Test
    public void shouldNotSetDomainUsersWhoArentSpenders() {
        Set<Long> userIds = new HashSet<>(2);
        userIds.add(thoraxSpender.getId());
        userIds.add(gkublokAdmin.getId());

        try {
            domainService.setDomainUsers(acatana.getId(), userIds);
            fail("Should've thrown an exception when attempting to set domain users who aren't all spenders");
        } catch (ServiceException expected) {
            assertTrue(expected.getMessage().contains(SPENDER_AUTHORITY_NAME));
        }
    }

    private void returnTwoSpenders() {
        Set<Long> userIds = new HashSet<>(2);
        userIds.add(thoraxSpender.getId());
        userIds.add(cobaltSpender.getId());

        List<User> users = new ArrayList<>(2);
        users.add(thoraxSpender);
        users.add(cobaltSpender);

        when(userRepository.findAll(userIds)).thenReturn(users);
    }

    private void returnSpenderAndAdmin() {
        Set<Long> userIds = new HashSet<>(2);
        userIds.add(thoraxSpender.getId());
        userIds.add(gkublokAdmin.getId());

        List<User> users = new ArrayList<>(2);
        users.add(thoraxSpender);
        users.add(gkublokAdmin);

        when(userRepository.findAll(userIds)).thenReturn(users);
    }

}

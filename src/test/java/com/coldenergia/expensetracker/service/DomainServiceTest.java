package com.coldenergia.expensetracker.service;

import com.coldenergia.expensetracker.builder.AuthorityBuilder;
import com.coldenergia.expensetracker.builder.DomainBuilder;
import com.coldenergia.expensetracker.builder.UserBuilder;
import com.coldenergia.expensetracker.domain.Domain;
import com.coldenergia.expensetracker.domain.User;
import com.coldenergia.expensetracker.repository.DomainRepository;
import com.coldenergia.expensetracker.repository.UserRepository;
import com.coldenergia.expensetracker.validator.DomainValidator;
import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.coldenergia.expensetracker.defaultdata.DefaultDataConstants.SPENDER_AUTHORITY_NAME;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * User: coldenergia
 * Date: 5/24/14
 * Time: 10:10 AM
 */
public class DomainServiceTest {

    private DomainRepository domainRepository;

    private UserRepository userRepository;

    private DomainService domainService;

    @Before
    public void setup() {
        domainRepository = mock(DomainRepository.class);
        userRepository = mock(UserRepository.class);
        domainService = new DomainServiceImpl(domainRepository, userRepository, new DomainValidator());
    }

    @Test
    public void shouldSaveDomain() {
        Domain domain = new DomainBuilder().withName("Acatana").build();
        domainService.save(domain);
        verify(domainRepository).save(domain);
    }

    @Test
    public void shouldNotSaveDomainWithEmptyName() {
        Domain invalid = new DomainBuilder().withName("").build();
        assertExceptionOnSave(invalid, "domain.name.empty");

        invalid = new DomainBuilder().withName(null).build();
        assertExceptionOnSave(invalid, "domain.name.empty");
    }

    @Test
    public void shouldNotSaveDomainWithNameExceedingFortyChars() {
        String invalidName = StringUtils.repeat("a", 41);
        Domain invalid = new DomainBuilder().withName(invalidName).build();
        assertExceptionOnSave(invalid, "domain.name.too.long");
    }

    @Test
    public void shouldSaveDomainWithNameNotExceedingFortyChars() {
        String validName = StringUtils.repeat("a", 40);
        Domain valid = new DomainBuilder().withName(validName).build();
        domainService.save(valid);
    }

    @Test
    public void shouldNotSaveDomainWithSpecialCharsInName() {
        Domain invalid = new DomainBuilder().withName("##$*&*#$").build();
        assertExceptionOnSave(invalid, "domain.name.contains.special.chars");

        invalid = new DomainBuilder().withName("white space").build();
        assertExceptionOnSave(invalid, "domain.name.contains.special.chars");

        invalid = new DomainBuilder().withName("dash-not-allowed").build();
        assertExceptionOnSave(invalid, "domain.name.contains.special.chars");
    }

    @Test
    public void shouldSaveDomainWithAlphanumericAndUnderscoreCharsInName() {
        Domain valid = new DomainBuilder().withName("Acatana_UT_2004").build();
        domainService.save(valid);
    }

    @Test
    public void shouldSetDomainUsers() {
        Long domainId = 4L;
        Set<Long> userIds = new HashSet<>(2);
        userIds.add(7L);
        userIds.add(2005L);

        prepareMocksForSetDomainUsers(userIds);

        domainService.setDomainUsers(domainId, userIds);

        verify(domainRepository).findOne(domainId);
        ArgumentCaptor<Domain> domainArgumentCaptor = ArgumentCaptor.forClass(Domain.class);
        verify(domainRepository).save(domainArgumentCaptor.capture());
        Domain target = domainArgumentCaptor.getValue();
        assertNotNull(target);
        assertTrue(target.getId() == 4L);
        assertTrue(target.getUsers().size() == 2);
        for (User u : target.getUsers()) {
            assertTrue(u.getId() == 7L || u.getId() == 2005L);
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
        Set<Long> emptyUserIdSet = new HashSet<>();
        User[] existingDomainUsers = {
                new UserBuilder().build(),
                new UserBuilder().build()
        };
        Domain domain = new DomainBuilder().withId(4L).withUsers(existingDomainUsers).build();
        when(domainRepository.findOne(4L)).thenReturn(domain);

        domainService.setDomainUsers(4L, emptyUserIdSet);

        ArgumentCaptor<Domain> domainArgumentCaptor = ArgumentCaptor.forClass(Domain.class);
        verify(domainRepository).save(domainArgumentCaptor.capture());
        Domain target = domainArgumentCaptor.getValue();
        assertNotNull(target);
        assertTrue(target.getUsers().isEmpty());
    }

    @Test
    public void shouldNotSetDomainUsersWhoArentSpenders() {
        Set<Long> userIds = new HashSet<>(2);
        userIds.add(7L);
        userIds.add(2005L);
        Domain domain = new DomainBuilder().withId(4L).build();
        List<User> users = new ArrayList<>(2);
        users.add(new UserBuilder().withId(7L).withAuthority(AuthorityBuilder.SPENDER_AUTHORITY).build());
        users.add(new UserBuilder().withId(2005L).withAuthority(AuthorityBuilder.ADMIN_AUTHORITY).build());
        when(userRepository.findAll(userIds)).thenReturn(users);
        when(domainRepository.findOne(4L)).thenReturn(domain);

        try {
            domainService.setDomainUsers(4L, userIds);
            fail("Should've thrown an exception when attempting to set domain users who aren't all spenders");
        } catch (ServiceException expected) {
            assertTrue(expected.getMessage().contains(SPENDER_AUTHORITY_NAME));
        }
    }

    private void prepareMocksForSetDomainUsers(Set<Long> userIds) {
        Domain domain = new DomainBuilder().withId(4L).build();
        User gkublok = new UserBuilder().withId(7L).withAuthority(AuthorityBuilder.SPENDER_AUTHORITY).build();
        User kraagesh = new UserBuilder().withId(2005L).withAuthority(AuthorityBuilder.SPENDER_AUTHORITY).build();
        List<User> users = new ArrayList<>(2);
        users.add(gkublok);
        users.add(kraagesh);
        when(domainRepository.findOne(4L)).thenReturn(domain);
        when(userRepository.findAll(userIds)).thenReturn(users);
    }

    private void assertExceptionOnSave(Domain invalid, String errorCode) {
        assertExceptionOnSave(invalid, errorCode, "Should've thrown an exception here");
    }

    private void assertExceptionOnSave(Domain invalid, String errorCode, String failMessage) {
        try {
            domainService.save(invalid);
            fail(failMessage);
        } catch (ServiceException expected) {
            assertNotNull(expected.getMessage());
            assertTrue(expected.getMessage().contains(errorCode));
        }
    }

}

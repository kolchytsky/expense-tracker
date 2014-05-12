package com.coldenergia.expensetracker.service;

import com.coldenergia.expensetracker.builder.AuthorityBuilder;
import com.coldenergia.expensetracker.domain.Authority;
import com.coldenergia.expensetracker.repository.AuthorityRepository;
import com.coldenergia.expensetracker.validator.AuthorityValidator;
import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * User: coldenergia
 * Date: 5/12/14
 * Time: 8:25 PM
 */
public class AuthorityServiceTest {

    private AuthorityRepository authorityRepository;

    private AuthorityService authorityService;

    @Before
    public void setup() {
        authorityRepository = mock(AuthorityRepository.class);
        authorityService = new AuthorityServiceImpl(authorityRepository, new AuthorityValidator());
    }

    @Test
    public void shouldSaveValidAuthority() {
        Authority authority = new AuthorityBuilder().build();
        authorityService.save(authority);
        verify(authorityRepository).save(authority);
    }

    @Test
    public void shouldNotSaveAuthorityWithEmptyName() {
        Authority invalid = new AuthorityBuilder().withName(null).build();
        assertExceptionOnSave(invalid, "authority.name.empty");
        invalid = new AuthorityBuilder().withName("").build();
        assertExceptionOnSave(invalid, "authority.name.empty");
    }

    @Test
    public void shouldNotSaveAuthorityWithNameExceedingSixtyChars() {
        String invalidName = StringUtils.repeat("a", 61);
        Authority invalid = new AuthorityBuilder().withName(invalidName).build();
        assertExceptionOnSave(invalid, "authority.name.too.long");
    }

    @Test
    public void shouldSaveAuthorityWithNameNotExceedingSixtyChars() {
        String validName = StringUtils.repeat("a", 60);
        Authority authority = new AuthorityBuilder().withName(validName).build();
        authorityService.save(authority);
    }

    @Test
    public void shouldNotSaveAuthorityWithSpecialCharsInName() {
        Authority invalid = new AuthorityBuilder().withName("##$*&*#$").build();
        assertExceptionOnSave(invalid, "authority.name.contains.special.chars");

        invalid = new AuthorityBuilder().withName("white space").build();
        assertExceptionOnSave(invalid, "authority.name.contains.special.chars");

        invalid = new AuthorityBuilder().withName("dash-not-allowed").build();
        assertExceptionOnSave(invalid, "authority.name.contains.special.chars");
    }

    @Test
    public void shouldSaveAuthorityWithAlphanumericAndUnderscoreCharsInName() {
        Authority valid = new AuthorityBuilder().withName("GUARDIAN_UT_2004").build();
        authorityService.save(valid);
    }

    private void assertExceptionOnSave(Authority invalid, String errorCode) {
        assertExceptionOnSave(invalid, errorCode, "Should've thrown an exception here");
    }

    private void assertExceptionOnSave(Authority invalid, String errorCode, String failMessage) {
        try {
            authorityService.save(invalid);
            fail(failMessage);
        } catch (ServiceException expected) {
            assertNotNull(expected.getMessage());
            assertTrue(expected.getMessage().contains(errorCode));
        }
    }

}

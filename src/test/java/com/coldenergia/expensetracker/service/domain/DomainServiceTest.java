package com.coldenergia.expensetracker.service.domain;

import com.coldenergia.expensetracker.builder.DomainBuilder;
import com.coldenergia.expensetracker.domain.Domain;
import com.coldenergia.expensetracker.repository.DomainRepository;
import com.coldenergia.expensetracker.repository.UserRepository;
import com.coldenergia.expensetracker.service.DomainService;
import com.coldenergia.expensetracker.service.DomainServiceImpl;
import com.coldenergia.expensetracker.service.ServiceException;
import com.coldenergia.expensetracker.validator.DomainValidator;
import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

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
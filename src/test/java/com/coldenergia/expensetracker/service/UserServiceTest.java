package com.coldenergia.expensetracker.service;

import com.coldenergia.expensetracker.builder.AuthorityBuilder;
import com.coldenergia.expensetracker.builder.UserBuilder;
import com.coldenergia.expensetracker.domain.Authority;
import com.coldenergia.expensetracker.domain.User;
import com.coldenergia.expensetracker.repository.AuthorityRepository;
import com.coldenergia.expensetracker.repository.UserRepository;
import com.coldenergia.expensetracker.validator.UserValidator;
import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

/**
 * User: coldenergia
 * Date: 5/10/14
 * Time: 8:02 PM
 */
public class UserServiceTest {

    private UserService userService;

    private AuthorityRepository authorityRepository;

    private UserRepository userRepository;

    @Before
    public void setup() {
        authorityRepository = mock(AuthorityRepository.class);
        userRepository = mock(UserRepository.class);
        userService = new UserServiceImpl(authorityRepository, userRepository, new UserValidator(), mock(PasswordEncoder.class));
    }

    @Test
    public void shouldSaveUser() {
        User valid = new UserBuilder().build();
        userService.save(valid);
        verify(userRepository).save(any(User.class));
    }

    @Test
    public void shouldNotSaveUserWithEmptyName() {
        User invalid = new UserBuilder().withName(null).build();
        assertExceptionOnSave(invalid, "user.name.empty");
        invalid = new UserBuilder().withName("").build();
        assertExceptionOnSave(invalid, "user.name.empty");
    }

    @Test
    public void shouldNotSaveUserWithoutAuthorities() {
        User invalid = new UserBuilder().withAuthorities(new Authority[] {}).build();
        assertExceptionOnSave(
                invalid,
                "user.authorities.empty",
                "Should've thrown an exception when attempting to save a user without authorities"
        );
    }

    @Test
    public void shouldNotSaveUserWithNameExceedingFortyChars() {
        String invalidName = StringUtils.repeat("a", 41);
        User invalid = new UserBuilder().withName(invalidName).build();
        assertExceptionOnSave(invalid, "user.name.too.long");
    }

    @Test
    public void shouldSaveUserWithNameNotExceedingFortyChars() {
        String validName = StringUtils.repeat("a", 40);
        User valid = new UserBuilder().withName(validName).build();
        userService.save(valid);
    }

    /**
     * Only allow letters (ASCII), digits and underscores.
     * */
    @Test
    public void shouldNotSaveUserWithSpecialCharsInName() {
        User invalid = new UserBuilder().withName("white space").build();
        assertExceptionOnSave(invalid, "user.name.contains.special.chars");

        invalid = new UserBuilder().withName("%*&&$this&is?INSANE").build();
        assertExceptionOnSave(invalid, "user.name.contains.special.chars");

        // Even dash is not allowed
        invalid = new UserBuilder().withName("simple-test").build();
        assertExceptionOnSave(invalid, "user.name.contains.special.chars");
    }

    @Test
    public void shouldSaveUserWithAlphanumbericAndUnderscoreCharsInName() {
        User valid = new UserBuilder().withName("Gkublok_UT_2004").build();
        userService.save(valid);
    }

    @Test
    public void shouldNotSaveUserWithEmptyEncodedPassword() {
        User invalid = new UserBuilder().withPassword(null).build();
        assertExceptionOnSave(invalid, "user.encoded.password.empty");
        invalid = new UserBuilder().withPassword("").build();
        assertExceptionOnSave(invalid, "user.encoded.password.empty");
    }

    @Test
    public void shouldNotSaveUserWithEncodedPasswordExceedingSixtyChars() {
        String unexpectedEncodedPassword = StringUtils.repeat("a", 61);
        User invalid = new UserBuilder().withPassword(unexpectedEncodedPassword).build();
        assertExceptionOnSave(invalid, "user.encoded.password.too.long");
    }

    @Test
    public void shouldSaveUserWithNewPassword() {
        User user = new UserBuilder().build();
        userService.saveUserWithNewPassword(user, "d0m1n4t0r");
        verify(userRepository).save(any(User.class));
    }

    @Test
    public void shouldNotSaveUserWithNewPasswordWithEmptyPassword() {
        User user = new UserBuilder().build();
        assertExceptionOnSaveUserWithNewPassword(user, null, "user.password.empty");
        user = new UserBuilder().build();
        assertExceptionOnSaveUserWithNewPassword(user, "", "user.password.empty");
    }

    @Test
    public void shouldNotSaveUserWithNewPasswordExceedingFiftyChars() {
        String invalidPwd = StringUtils.repeat("a", 51);
        User user = new UserBuilder().build();
        assertExceptionOnSaveUserWithNewPassword(user, invalidPwd, "user.password.too.long");
    }

    @Test
    public void shouldSaveUserWithNewPasswordNotExceedingFiftyChars() {
        String validPwd = StringUtils.repeat("a", 50);
        User user = new UserBuilder().build();
        userService.saveUserWithNewPassword(user, validPwd);
    }

    @Test
    public void shouldNotSaveUserWithNewPasswordWithNonASCIICharsInPassword() {
        User user = new UserBuilder().build();
        assertExceptionOnSaveUserWithNewPassword(user, "Ukrainian_char:\u0456", "user.password.non.ascii");
    }

    @Test
    public void shouldSaveUserWithNewPasswordWithSpecialCharsInPassword() {
        User user = new UserBuilder().build();
        userService.saveUserWithNewPassword(user, "&#*@&$@#_ 34/\\");
    }

    @Test
    public void shouldSetUserCreatedDate() {
        User gkublok = new UserBuilder().withId(null).withCreated(null).build();
        userService.save(gkublok);
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userArgumentCaptor.capture());
        User capturedGkublok = userArgumentCaptor.getValue();
        assertNotNull(capturedGkublok.getCreated());
    }

    @Test
    public void shouldNotSetCreatedDateWhenUpdatingUser() throws Exception {
        Date gkublokDate = new SimpleDateFormat("MM/dd/yyyy").parse("03/16/2004");
        User gkublok = new UserBuilder().withId(1L).withCreated(gkublokDate).build();
        when(userRepository.exists(1L)).thenReturn(true);
        userService.save(gkublok);
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).exists(1L);
        verify(userRepository).save(userArgumentCaptor.capture());
        User capturedGkublok = userArgumentCaptor.getValue();
        assertEquals(gkublokDate, capturedGkublok.getCreated());
    }

    @Test
    public void shouldFindUserByName() {
        User gkublok = new UserBuilder().withName("Gkublok").build();
        when(userRepository.findByName("Gkublok")).thenReturn(gkublok);
        User retrievedGkublok = userService.findByName("Gkublok");
        verify(userRepository).findByName("Gkublok");
        assertEquals(gkublok, retrievedGkublok);
    }

    @Test
    public void shouldSaveUserWithAuthorityNames() {
        Authority authority = new AuthorityBuilder().withName("defender").build();
        when(authorityRepository.findByName("defender")).thenReturn(authority);
        User guardian = new UserBuilder().withAuthorities(new ArrayList<Authority>()).withName("Guardian").build();
        Set<String> authorityNames = new HashSet<>(1);
        authorityNames.add("defender");

        userService.saveUserWithNewPassword(guardian, authorityNames, "somepassword");

        ArgumentCaptor<User> guardianCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(guardianCaptor.capture());

        User capturedGuardian = guardianCaptor.getValue();
        assertFalse(capturedGuardian.getAuthorities().isEmpty());
        assertEquals(authority.getName(), capturedGuardian.getAuthorities().get(0).getName());
    }

    private void assertExceptionOnSave(User invalid, String errorCode) {
        assertExceptionOnSave(invalid, errorCode, "Should've thrown an exception here");
    }

    private void assertExceptionOnSave(User invalid, String errorCode, String failMessage) {
        try {
            userService.save(invalid);
            fail(failMessage);
        } catch (ServiceException expected) {
            assertNotNull(expected.getMessage());
            assertTrue(expected.getMessage().contains(errorCode));
        }
    }

    private void assertExceptionOnSaveUserWithNewPassword(User invalid, String rawPassword, String errorCode) {
        try {
            userService.saveUserWithNewPassword(invalid, rawPassword);
            fail("Should've thrown an exception here");
        } catch (ServiceException expected) {
            assertNotNull(expected.getMessage());
            assertTrue(expected.getMessage().contains(errorCode));
        }
    }

}

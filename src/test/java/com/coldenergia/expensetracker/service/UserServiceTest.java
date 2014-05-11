package com.coldenergia.expensetracker.service;

import com.coldenergia.expensetracker.builder.UserBuilder;
import com.coldenergia.expensetracker.domain.Authority;
import com.coldenergia.expensetracker.domain.User;
import com.coldenergia.expensetracker.repository.UserRepository;
import com.coldenergia.expensetracker.validator.UserValidator;
import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * User: coldenergia
 * Date: 5/10/14
 * Time: 8:02 PM
 */
public class UserServiceTest {

    private UserService userService;

    private UserRepository userRepository;

    @Before
    public void setup() {
        userRepository = mock(UserRepository.class);
        userService = new UserServiceImpl(userRepository, new UserValidator());
    }

    @Test
    public void shouldSaveValidUser() {
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
    public void shouldNotSaveUserWithEmptyPassword() {
        User invalid = new UserBuilder().withPassword(null).build();
        assertExceptionOnSave(invalid, "user.password.empty");
        invalid = new UserBuilder().withPassword("").build();
        assertExceptionOnSave(invalid, "user.password.empty");
    }

    @Test
    public void shouldNotSaveUserWithPasswordExceedingFiftyChars() {
        String invalidPwd = StringUtils.repeat("a", 51);
        User invalid = new UserBuilder().withPassword(invalidPwd).build();
        assertExceptionOnSave(invalid, "user.password.too.long");
    }

    @Test
    public void shouldSaveUserWithPasswordNotExceedingFiftyChars() {
        String validPwd = StringUtils.repeat("a", 50);
        User valid = new UserBuilder().withPassword(validPwd).build();
        userService.save(valid);
    }

    @Test
    public void shouldNotSaveUserWithNonASCIICharsInPassword() {
        User invalid = new UserBuilder().withPassword("Ukrainian_char:\u0456").build();
        assertExceptionOnSave(invalid, "user.password.non.ascii");
    }

    @Test
    public void shouldSaveUserWithSpecialCharsInPassword() {
        User valid = new UserBuilder().withPassword("&#*@&$@#_ 34/\\").build();
        userService.save(valid);
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

}

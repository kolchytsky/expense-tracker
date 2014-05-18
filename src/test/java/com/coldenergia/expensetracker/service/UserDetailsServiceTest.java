package com.coldenergia.expensetracker.service;

import com.coldenergia.expensetracker.builder.AuthorityBuilder;
import com.coldenergia.expensetracker.builder.UserBuilder;
import com.coldenergia.expensetracker.domain.Authority;
import com.coldenergia.expensetracker.domain.User;
import com.coldenergia.expensetracker.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * User: coldenergia
 * Date: 5/6/14
 * Time: 7:44 PM
 */
public class UserDetailsServiceTest {

    private UserDetailsService userDetailsService;

    private UserRepository userRepository;

    @Before
    public void setup() {
        userRepository = mock(UserRepository.class);
        userDetailsService = new UserDetailsServiceImpl(userRepository);
    }

    @Test
    public void shouldLoadUserByUsername() {
        User gkublok = new UserBuilder().withName("Gkublok").build();
        when(userRepository.findByName("Gkublok")).thenReturn(gkublok);
        UserDetails userDetails = userDetailsService.loadUserByUsername("Gkublok");
        verify(userRepository).findByName("Gkublok");
        assertNotNull(userDetails);
        assertEquals("Gkublok", userDetails.getUsername());
        assertNotNull("Had to retrieve user password", userDetails.getPassword());
    }

    @Test
    public void shouldPullAuthoritiesForLoadedUser() {
        Authority[] authorities = {
                new AuthorityBuilder().withName("ADMIN").build(),
                new AuthorityBuilder().withName("Skaarj").build()
        };
        User guardian = new UserBuilder().withName("Guardian").withAuthorities(authorities).build();
        when(userRepository.findByName("Guardian")).thenReturn(guardian);
        UserDetails userDetails = userDetailsService.loadUserByUsername("Guardian");
        assertNotNull(userDetails);
        assertNotNull(userDetails.getAuthorities());
        assertEquals(2, userDetails.getAuthorities().size());
    }

    @Test
    public void shouldThrowExceptionForNonExistingUser() {
        when(userRepository.findByName("Mandible")).thenReturn(null);
        try {
            userDetailsService.loadUserByUsername("Mandible");
            fail("Should've thrown an UsernameNotFoundException here.");
        } catch (UsernameNotFoundException expected) {
            assertNotNull(expected.getMessage());
            assertEquals("User with name 'Mandible' not found", expected.getMessage());
        }
    }

    @Test
    public void shouldThrowExceptionForUserWithNoGrantedAuthorities() {
        User userWithoutAuthorities = new UserBuilder().withName("Guardian").withAuthorities(new ArrayList<Authority>()).build();
        when(userRepository.findByName("Guardian")).thenReturn(userWithoutAuthorities);
        try {
            userDetailsService.loadUserByUsername("Guardian");
            fail("Should've thrown an UsernameNotFoundException here.");
        } catch (UsernameNotFoundException expected) {
            assertNotNull(expected.getMessage());
            assertEquals("User with name 'Guardian' has no authorities", expected.getMessage());
        }
    }

}

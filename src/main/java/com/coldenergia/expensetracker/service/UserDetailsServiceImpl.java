package com.coldenergia.expensetracker.service;

import com.coldenergia.expensetracker.domain.Authority;
import com.coldenergia.expensetracker.domain.User;
import com.coldenergia.expensetracker.domain.UserSecurityDetails;
import com.coldenergia.expensetracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/*
* Without @Transactional I had org.hibernate.LazyInitializationException - perhaps
* I could create a test for such behaviour.
* */
/**
 * User: coldenergia
 * Date: 5/6/14
 * Time: 8:17 PM
 */
@Service
@Transactional(readOnly = true)
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * @see UserDetailsService#loadUserByUsername(String)
     * */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByName(username);
        if (user == null) {
            throw new UsernameNotFoundException("User with name '" + username + "' not found");
        }
        UserSecurityDetails userDetails = new UserSecurityDetails();
        userDetails.setUsername(username);
        userDetails.setPassword(user.getPassword());
        List<Authority> authorities = user.getAuthorities();
        // According to UserDetailsService contract, the user must have GrantedAuthorities
        if (authorities == null || authorities.isEmpty()) {
            throw new UsernameNotFoundException("User with name '" + username + "' has no authorities");
        }
        List<String> authorityNames = getAuthorityNamesFromAuthorities(authorities);
        userDetails.setAuthorities(authorityNames);
        return userDetails;
    }

    private List<String> getAuthorityNamesFromAuthorities(List<Authority> authorities) {
        List<String> authorityNames = new ArrayList<String>(authorities.size());
        for (Authority authority : authorities) {
            authorityNames.add(authority.getName());
        }
        return authorityNames;
    }

}

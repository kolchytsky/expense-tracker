package com.coldenergia.expensetracker.service;

import com.coldenergia.expensetracker.domain.Authority;
import com.coldenergia.expensetracker.domain.User;
import com.coldenergia.expensetracker.repository.AuthorityRepository;
import com.coldenergia.expensetracker.repository.UserRepository;
import com.coldenergia.expensetracker.validator.UserValidator;
import com.coldenergia.expensetracker.validator.ValidationResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * User: coldenergia
 * Date: 5/10/14
 * Time: 8:18 PM
 */
@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final AuthorityRepository authorityRepository;

    private final UserRepository userRepository;

    private final UserValidator userValidator;

    private final PasswordEncoder passwordEncoder;

    /**
     * That's where I finally decided to abandon field injection,
     * as the test configuration became more and more verbose and complicated,
     * and <a href="http://www.olivergierke.de/2013/11/why-field-injection-is-evil/">
     *     field injection is evil</a>.
     * */
    @Autowired
    public UserServiceImpl(
            AuthorityRepository authorityRepository,
            UserRepository userRepository,
            UserValidator userValidator,
            PasswordEncoder passwordEncoder) {
        this.authorityRepository = authorityRepository;
        this.userRepository = userRepository;
        this.userValidator = userValidator;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    @Override
    public User save(User user) {
        validate(user);
        user = saveUser(user);
        return user;
    }

    @Transactional
    @Override
    public User saveUserWithNewPassword(User user, String rawPassword) {
        validate(user, rawPassword);
        user.setPassword(passwordEncoder.encode(rawPassword));
        user = saveUser(user);
        return user;
    }

    @Transactional
    @Override
    public User saveUserWithNewPassword(User user, Set<String> authorityNames, String rawPassword) {
        List<Authority> authorities = retrieveAuthoritiesByNames(authorityNames);
        // Surely whatever was in user authorities is discarded at this point
        user.setAuthorities(authorities);
        return saveUserWithNewPassword(user, rawPassword);
    }

    @Override
    public User findByName(String name) {
        return userRepository.findByName(name);
    }

    private User saveUser(User user) {
        if (!exists(user)) {
            user.setCreated(new Date());
        }
        if (isUserNameAlreadyTaken(user.getName())) {
            throw new UserNameIsTakenException("Name " + user.getName() + " has already been taken.");
        }
        User u = userRepository.save(user);
        return u;
    }

    private boolean isUserNameAlreadyTaken(String name) {
        return userRepository.findByName(name) != null;
    }

    private boolean exists(User user) {
        if (user == null || user.getId() == null) {
            return false;
        }
        if (!userRepository.exists(user.getId())) {
            return false;
        }
        return true;
    }

    private List<Authority> retrieveAuthoritiesByNames(Set<String> authorityNames) {
        List<Authority> authorities = new ArrayList<>(authorityNames.size());
        for (String authorityName : authorityNames) {
            Authority authority = authorityRepository.findByName(authorityName);
            if (authority != null) {
                authorities.add(authority);
            }
        }
        return authorities;
    }

    private void validate(User user) {
        ValidationResult result = userValidator.validate(user);
        if (result.hasErrors()) {
            throw new ServiceException(result.getAggregatedErrorCodes());
        }
    }

    private void validate(User user, String rawPassword) {
        ValidationResult result = userValidator.validate(user, rawPassword);
        if (result.hasErrors()) {
            throw new ServiceException(result.getAggregatedErrorCodes());
        }
    }

}

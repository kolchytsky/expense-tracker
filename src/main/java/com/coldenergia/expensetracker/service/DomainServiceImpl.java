package com.coldenergia.expensetracker.service;

import com.coldenergia.expensetracker.domain.Authority;
import com.coldenergia.expensetracker.domain.Domain;
import com.coldenergia.expensetracker.domain.User;
import com.coldenergia.expensetracker.repository.DomainRepository;
import com.coldenergia.expensetracker.repository.UserRepository;
import com.coldenergia.expensetracker.validator.DomainValidator;
import com.coldenergia.expensetracker.validator.ValidationResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.coldenergia.expensetracker.defaultdata.DefaultDataConstants.SPENDER_AUTHORITY_NAME;

/**
 * User: coldenergia
 * Date: 5/24/14
 * Time: 10:15 AM
 */
@Service
@Transactional(readOnly = true)
public class DomainServiceImpl implements DomainService {

    private final DomainRepository domainRepository;

    private final UserRepository userRepository;

    private final DomainValidator domainValidator;

    @Autowired
    public DomainServiceImpl(
            DomainRepository domainRepository,
            UserRepository userRepository,
            DomainValidator domainValidator) {
        this.domainRepository = domainRepository;
        this.userRepository = userRepository;
        this.domainValidator = domainValidator;
    }

    @Transactional
    @Override
    public Domain save(Domain domain) {
        validate(domain);
        return domainRepository.save(domain);
    }

    @Transactional
    @Override
    public Domain setDomainUsers(Long domainId, Set<Long> userIds) {
        Domain domain = domainRepository.findOne(domainId);
        List<User> users = retrieveUsers(userIds);
        domain.setUsers(users);
        return save(domain);
    }

    private List<User> retrieveUsers(Iterable<Long> userIds) {
        Iterable<User> users = userRepository.findAll(userIds);
        if (users == null || !users.iterator().hasNext()) {
            return new ArrayList<>();
        }
        List<User> userList = new ArrayList<>();
        for (User user : users) {
            if (!hasSpenderAuthority(user)) {
                String msg = "User " + user.getName() + " doesn't have " + SPENDER_AUTHORITY_NAME + " authority.";
                throw new ServiceException(msg);
            }
            userList.add(user);
        }
        return userList;
    }

    private boolean hasSpenderAuthority(User user) {
        boolean hasSpenderAuthority = false;
        for (Authority authority : user.getAuthorities()) {
            if (authority.getName().contains(SPENDER_AUTHORITY_NAME)) {
                hasSpenderAuthority = true;
                break;
            }
        }
        return hasSpenderAuthority;
    }

    private void validate(Domain domain) {
        ValidationResult result = domainValidator.validate(domain);
        if (result.hasErrors()) {
            throw new ServiceException(result.getAggregatedErrorCodes());
        }
    }

}

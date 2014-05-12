package com.coldenergia.expensetracker.service;

import com.coldenergia.expensetracker.domain.Authority;
import com.coldenergia.expensetracker.repository.AuthorityRepository;
import com.coldenergia.expensetracker.validator.AuthorityValidator;
import com.coldenergia.expensetracker.validator.ValidationResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * User: coldenergia
 * Date: 5/12/14
 * Time: 8:34 PM
 */
// TODO: Notice - again no transactional declarations
@Service
public class AuthorityServiceImpl implements AuthorityService {

    private final AuthorityRepository authorityRepository;

    private final AuthorityValidator authorityValidator;

    @Autowired
    public AuthorityServiceImpl(AuthorityRepository authorityRepository, AuthorityValidator authorityValidator) {
        this.authorityRepository = authorityRepository;
        this.authorityValidator = authorityValidator;
    }

    @Override
    public Authority save(Authority authority) {
        validate(authority);
        return authorityRepository.save(authority);
    }

    private void validate(Authority authority) {
        ValidationResult result = authorityValidator.validate(authority);
        if (result.hasErrors()) {
            throw new ServiceException(result.getAggregatedErrorCodes());
        }
    }

}

package com.coldenergia.expensetracker.service;

import com.coldenergia.expensetracker.domain.Authority;
import com.coldenergia.expensetracker.domain.Category;
import com.coldenergia.expensetracker.domain.Domain;
import com.coldenergia.expensetracker.domain.User;
import com.coldenergia.expensetracker.repository.CategoryRepository;
import com.coldenergia.expensetracker.repository.DomainRepository;
import com.coldenergia.expensetracker.repository.UserRepository;
import com.coldenergia.expensetracker.validator.DomainValidator;
import com.coldenergia.expensetracker.validator.ValidationResult;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

import static com.coldenergia.expensetracker.ancillary.CollectionUtils.listFromIterable;
import static com.coldenergia.expensetracker.defaultdata.DefaultDataConstants.ROOT_CATEGORY_NAME;
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

    private final CategoryRepository categoryRepository;

    private final UserRepository userRepository;

    private final DomainValidator domainValidator;

    @Autowired
    public DomainServiceImpl(
            DomainRepository domainRepository,
            CategoryRepository categoryRepository,
            UserRepository userRepository,
            DomainValidator domainValidator) {
        this.domainRepository = domainRepository;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
        this.domainValidator = domainValidator;
    }

    @Transactional
    @Override
    public Domain save(Domain domain) {
        validate(domain);

        boolean isNewDomain = (domain.getId() == null || !domainRepository.exists(domain.getId()));
        if (isNewDomain) {
            if (isDomainNameAlreadyTaken(domain.getName())) {
                throw new DomainNameIsTakenException("Name " + domain.getName() + " has already been taken");
            }
            createRootCategoryForDomain(domain);
        }

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

    @Override
    public Domain findOne(Long id) {
        return domainRepository.findOne(id);
    }

    @Override
    public Domain findOneAccessibleByUser(Long domainId, String userName) {
        return domainRepository.findOneAccessibleByUser(domainId, userName);
    }

    @Override
    public Domain findOneAndInitUserList(Long id) {
        Domain domain = findOne(id);
        // Initialize lazy-loaded user list by force
        Hibernate.initialize(domain.getUsers());
        return domain;
    }

    @Override
    public List<Domain> findDomainsAccessibleByUser(Long userId) {
        return domainRepository.findDomainsAccessibleByUser(userId);
    }

    @Override
    public List<Domain> findDomainsAccessibleByUser(String userName) {
        return domainRepository.findDomainsAccessibleByUser(userName);
    }

    @Override
    public List<Domain> findAll() {
        Iterable<Domain> domains = domainRepository.findAll();
        return listFromIterable(domains);
    }

    private void createRootCategoryForDomain(Domain domain) {
        Category category = new Category();
        category.setName(ROOT_CATEGORY_NAME);
        category.setDomain(domain);
        category.setParentCategory(null);
        category.setChildCategories(null);

        categoryRepository.save(category);
    }

    private List<User> retrieveUsers(Iterable<Long> userIds) {
        Iterable<User> users = userRepository.findAll(userIds);
        List<User> userList = listFromIterable(users);
        checkThatUserListContainsSpendersOnly(userList);
        return userList;
    }

    private boolean isDomainNameAlreadyTaken(String name) {
        return domainRepository.findByName(name) != null;
    }

    /**
     * @throws ServiceException If any of the users doesn't have a SPENDER authority.
     * */
    private void checkThatUserListContainsSpendersOnly(List<User> users) {
        for (User user : users) {
            if (!hasSpenderAuthority(user)) {
                String msg = "User " + user.getName() + " doesn't have " + SPENDER_AUTHORITY_NAME + " authority.";
                throw new ServiceException(msg);
            }
        }
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

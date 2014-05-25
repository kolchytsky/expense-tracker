package com.coldenergia.expensetracker.service.domain;

import com.coldenergia.expensetracker.repository.CategoryRepository;
import com.coldenergia.expensetracker.repository.DomainRepository;
import com.coldenergia.expensetracker.repository.UserRepository;
import com.coldenergia.expensetracker.service.DomainService;
import com.coldenergia.expensetracker.service.DomainServiceImpl;
import com.coldenergia.expensetracker.validator.DomainValidator;
import org.junit.Before;

import static org.mockito.Mockito.mock;

/**
 * User: coldenergia
 * Date: 5/25/14
 * Time: 3:47 PM
 */
public class AbstractDomainServiceTest {

    protected DomainRepository domainRepository;

    protected CategoryRepository categoryRepository;

    protected UserRepository userRepository;

    protected DomainService domainService;

    @Before
    public void setup() {
        domainRepository = mock(DomainRepository.class);
        categoryRepository = mock(CategoryRepository.class);
        userRepository = mock(UserRepository.class);
        domainService = new DomainServiceImpl(
                domainRepository,
                categoryRepository,
                userRepository,
                new DomainValidator()
        );
    }

}

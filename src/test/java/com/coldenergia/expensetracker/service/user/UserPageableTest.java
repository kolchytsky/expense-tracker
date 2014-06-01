package com.coldenergia.expensetracker.service.user;

import org.junit.Before;
import org.junit.Test;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import static org.mockito.Mockito.verify;

/**
 * User: coldenergia
 * Date: 6/1/14
 * Time: 11:46 AM
 */
public class UserPageableTest extends AbstractUserServiceTest {

    @Before
    public void setup() {
        super.setup();
    }

    @Test
    public void shouldFindAllUsersWithPageableSupport() {
        Pageable pageable = new Pageable() {
            @Override
            public int getPageNumber() {
                return 1;
            }
            @Override
            public int getPageSize() {
                return 20;
            }
            @Override
            public int getOffset() {
                return 0;
            }
            @Override
            public Sort getSort() {
                return null;
            }
            @Override
            public Pageable next() {
                return null;
            }
            @Override
            public Pageable previousOrFirst() {
                return null;
            }
            @Override
            public Pageable first() {
                return null;
            }
            @Override
            public boolean hasPrevious() {
                return false;
            }
        };
        userService.findAll(pageable);
        verify(userRepository).findAll(pageable);
    }

}

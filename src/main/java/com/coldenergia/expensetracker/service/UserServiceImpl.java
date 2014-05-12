package com.coldenergia.expensetracker.service;

import com.coldenergia.expensetracker.domain.User;
import com.coldenergia.expensetracker.repository.UserRepository;
import com.coldenergia.expensetracker.validator.UserValidator;
import com.coldenergia.expensetracker.validator.ValidationResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * User: coldenergia
 * Date: 5/10/14
 * Time: 8:18 PM
 */
@Service
// TODO: Create an integration test for transaction demarcation test
//@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserValidator userValidator;

    /**
     * That's where I finally decided to abandon field injection,
     * as the test configuration became more and more verbose and complicated,
     * and <a href="http://www.olivergierke.de/2013/11/why-field-injection-is-evil/">
     *     field injection is evil</a>.
     * */
    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserValidator userValidator) {
        this.userRepository = userRepository;
        this.userValidator = userValidator;
    }

    @Override
    public User save(User user) {
        validate(user);
        if (!exists(user)) {
            user.setCreated(new Date());
        }
        User u = userRepository.save(user);
        return u;
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

    private void validate(User user) {
        ValidationResult result = userValidator.validate(user);
        if (result.hasErrors()) {
            throw new ServiceException(result.getAggregatedErrorCodes());
        }
    }

    // THIS IS DITCHED NOW...
    /**
     * @param objectName I don't think it serves any important purpose, perhaps it is more for web layer
     *                   (name of the object when JSTL is being used)
     * @throws ServiceException As of now this exception is being thrown if any errors are found. Also, the exception message
     * contains the error codes.
     * */
    /*private static void validate(Validator validator, Object objectToValidate, String objectName) {
        BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(objectToValidate, objectName);
        validator.validate(objectToValidate, bindingResult);
        if (bindingResult.hasErrors()) {
            StringBuilder errorMessageAggregator = new StringBuilder();
            for (ObjectError error : bindingResult.getAllErrors()) {
                String errorCode = error.getCode();
                errorMessageAggregator.append(errorCode).append(",\n");
            }
            throw new ServiceException(errorMessageAggregator.toString());
        }
    }*/

}

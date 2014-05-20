package com.coldenergia.expensetracker.web.view.model.validator;

import com.coldenergia.expensetracker.web.view.model.UserForm;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import static com.coldenergia.expensetracker.validator.RegexPatterns.ALPHANUMERIC_AND_UNDERSCORES_ONLY;
import static com.coldenergia.expensetracker.validator.RegexPatterns.ASCII_CHARS_ONLY;
import static org.springframework.validation.ValidationUtils.rejectIfEmpty;

/**
 * This class duplicates much of the validation declared in
 * {@link com.coldenergia.expensetracker.validator.UserValidator}. However, I find it justified
 * to have validation at both service and controller level. Also, as the validation in service
 * level is covered with tests, this validation is not.<br>
 * User: coldenergia
 * Date: 5/20/14
 * Time: 9:00 PM
 */
@Component
public class UserFormValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return UserForm.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserForm userForm = (UserForm) target;
        rejectIfEmpty(errors, "name", "user.name.empty");
        String name = userForm.getName();
        if (name != null) {
            if (name.length() > 40) {
                errors.rejectValue("name", "user.name.too.long");
            }
            if (!name.matches(ALPHANUMERIC_AND_UNDERSCORES_ONLY)) {
                errors.rejectValue("name", "user.name.contains.special.chars");
            }
        }

        String rawPassword = userForm.getPassword();
        rejectIfEmpty(errors, "password", "user.password.empty");
        if (rawPassword != null) {
            if (rawPassword.length() > 50) {
                errors.reject("password", "user.password.too.long");
            }
            // Password must be comprised solely of ASCII characters.
            if (!rawPassword.matches(ASCII_CHARS_ONLY)) {
                errors.reject("password", "user.password.non.ascii");
            }
        }

        String authority = userForm.getAuthority();
        if (isAuthorityInvalid(authority)) {
            errors.rejectValue("authority", "user.authorities.no.authority.was.chosen");
        }
    }

    private boolean isAuthorityInvalid(String authority) {
        return (!"admin".equals(authority) && !"spender".equals(authority));
    }

}

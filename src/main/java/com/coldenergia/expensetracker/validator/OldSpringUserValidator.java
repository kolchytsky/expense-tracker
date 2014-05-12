package com.coldenergia.expensetracker.validator;

import com.coldenergia.expensetracker.domain.User;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import static org.springframework.validation.ValidationUtils.rejectIfEmpty;
import static org.springframework.validation.ValidationUtils.rejectIfEmptyOrWhitespace;

import static com.coldenergia.expensetracker.validator.RegexPatterns.ALPHANUMERIC_AND_UNDERSCORES_ONLY;
import static com.coldenergia.expensetracker.validator.RegexPatterns.ASCII_CHARS_ONLY;

/**
 * User: coldenergia
 * Date: 5/11/14
 * Time: 10:42 AM
 */
/**
 * I've decided to use such validators in the service layer, despite
 * what that <a href="http://www.petrikainulainen.net/software-development/design/the-biggest-flaw-of-spring-web-applications/">
 * article</a> says. I don't really want my domain model cluttered with all sorts of annotations,
 * even if they are like those JSR ones - @NotNull and so on - because I am sure there want be enough
 * annotations to cover all potential cases.
 * */
//@Component
public class OldSpringUserValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;

        rejectIfEmpty(errors, "name", "user.name.empty");
        String name = user.getName();
        if (user.getAuthorities() == null || user.getAuthorities().isEmpty()) {
            errors.rejectValue("authorities", "user.authorities.empty");
        }
        if (name != null) {
            if (name.length() > 40) {
                errors.rejectValue("name", "user.name.too.long");
            }
            if (!name.matches(ALPHANUMERIC_AND_UNDERSCORES_ONLY)) {
                errors.rejectValue("name", "user.name.contains.special.chars");
            }
        }

        rejectIfEmpty(errors, "password", "user.password.empty");
        String password = user.getPassword();
        if (password != null) {
            if (password.length() > 50) {
                errors.rejectValue("password", "user.password.too.long");
            }
            // Password must be comprised solely of ASCII characters.
            if (!password.matches(ASCII_CHARS_ONLY)) {
                errors.rejectValue("password", "user.password.non.ascii");
            }
        }
    }

}

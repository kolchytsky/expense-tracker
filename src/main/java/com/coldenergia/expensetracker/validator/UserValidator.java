package com.coldenergia.expensetracker.validator;

import com.coldenergia.expensetracker.domain.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import static com.coldenergia.expensetracker.validator.RegexPatterns.ALPHANUMERIC_AND_UNDERSCORES_ONLY;
import static com.coldenergia.expensetracker.validator.RegexPatterns.ASCII_CHARS_ONLY;

/**
 * I've decided to use such validators in the service layer, despite
 * what that <a href="http://www.petrikainulainen.net/software-development/design/the-biggest-flaw-of-spring-web-applications/">
 * article</a> says. I don't really want my domain model cluttered with all sorts of annotations,
 * even if they are like those JSR ones - @NotNull and so on - because I am sure there want be enough
 * annotations to cover all potential cases.<br>
 * User: coldenergia
 * Date: 5/12/14
 * Time: 10:22 PM
 */
@Component
public class UserValidator {

    public ValidationResult validate(User user) {
        ValidationResult result = new ValidationResult();

        validateCommonAttributes(user, result);

        String encodedPassword = user.getPassword();
        if (StringUtils.isEmpty(encodedPassword)) {
            result.rejectValue("password", "user.encoded.password.empty");
        }
        if (encodedPassword != null) {
            if (encodedPassword.length() > 60) {
                result.rejectValue("password", "user.encoded.password.too.long");
            }
        }

        return result;
    }

    public ValidationResult validate(User user, String rawPassword) {
        ValidationResult result = new ValidationResult();

        validateCommonAttributes(user, result);

        if (StringUtils.isEmpty(rawPassword)) {
            result.reject("user.password.empty");
        }
        if (rawPassword != null) {
            if (rawPassword.length() > 50) {
                result.reject("user.password.too.long");
            }
            // Password must be comprised solely of ASCII characters.
            if (!rawPassword.matches(ASCII_CHARS_ONLY)) {
                result.reject("user.password.non.ascii");
            }
        }

        return result;
    }

    private void validateCommonAttributes(User user, ValidationResult result) {
        String name = user.getName();
        if (StringUtils.isEmpty(name)) {
            result.rejectValue("name", "user.name.empty");
        }
        if (user.getAuthorities() == null || user.getAuthorities().isEmpty()) {
            result.rejectValue("authorities", "user.authorities.empty");
        }
        if (name != null) {
            if (name.length() > 40) {
                result.rejectValue("name", "user.name.too.long");
            }
            if (!name.matches(ALPHANUMERIC_AND_UNDERSCORES_ONLY)) {
                result.rejectValue("name", "user.name.contains.special.chars");
            }
        }
    }

}

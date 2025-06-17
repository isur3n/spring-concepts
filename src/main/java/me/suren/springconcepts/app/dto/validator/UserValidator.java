package me.suren.springconcepts.app.dto.validator;

import lombok.extern.slf4j.Slf4j;
import me.suren.springconcepts.app.dto.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Slf4j
@Component
public class UserValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User validateMe = (User) target;
        log.info("Validation started for - {}", target);

        if(StringUtils.isBlank(validateMe.getName()) ||
                validateMe.getName().length() < 2) {
            errors.rejectValue("name", "001", "Can't be blank or smaller than 2 characters");
        }

        if(StringUtils.isBlank(validateMe.getEmail()) ||
                validateMe.getEmail().length() < 10) {
            errors.rejectValue("email", "003", "Can't be blank or smaller than 10 characters");
        }
    }
}

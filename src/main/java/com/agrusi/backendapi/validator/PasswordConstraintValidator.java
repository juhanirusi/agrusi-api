package com.agrusi.backendapi.validator;

import com.agrusi.backendapi.validator.annotation.ValidPassword;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.passay.*;

import java.util.Arrays;
import java.util.List;

public class PasswordConstraintValidator implements
        ConstraintValidator<ValidPassword, String> {

    @Override
    public void initialize(ValidPassword constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {

        // If the password field is null or non-existent in the JSON payload...
        if (password == null) {
            return false;
        }

        PasswordValidator validator = new PasswordValidator(Arrays.asList(

                // Password length between 8 - 30 characters
                new LengthRule(8, 30),

                // At least one uppercase letter
                new CharacterRule(EnglishCharacterData.UpperCase, 1),

                // At least one digit character (0 - 9)
                new CharacterRule(EnglishCharacterData.Digit, 1),

                // No whitespace
                new WhitespaceRule()));

        RuleResult result = validator.validate(new PasswordData(password));

        if (result.isValid()) {
            return true;
        }

        List<String> messages = validator.getMessages(result);

        String messageTemplate = String.join(" ", messages);

        context.buildConstraintViolationWithTemplate(messageTemplate)
                .addConstraintViolation()
                .disableDefaultConstraintViolation();

        return false;
    }
}

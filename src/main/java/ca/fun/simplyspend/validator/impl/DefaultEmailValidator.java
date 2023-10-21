package ca.fun.simplyspend.validator.impl;

import ca.fun.simplyspend.data.CreateUserValidationResult;
import ca.fun.simplyspend.data.UserField;
import ca.fun.simplyspend.validator.EmailValidator;
import jakarta.inject.Singleton;

@Singleton
public class DefaultEmailValidator implements EmailValidator {

    org.apache.commons.validator.routines.EmailValidator emailValidator = org.apache.commons.validator.routines.EmailValidator.getInstance();

    public CreateUserValidationResult.ValidationResult isValid(String email) {
        if (emailValidator.isValid(email)) {
            return new CreateUserValidationResult.ValidationResult(
                    UserField.EMAIL_ID,
                    "",
                    true
            );
        }
        return new CreateUserValidationResult.ValidationResult(
                UserField.EMAIL_ID,
                "Invalid email address",
                false
        );
    }

}

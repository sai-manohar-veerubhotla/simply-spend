package ca.fun.simplyspend.validator.impl;

import ca.fun.simplyspend.data.CreateUserValidationResult;
import ca.fun.simplyspend.data.PasswordConfig;
import ca.fun.simplyspend.data.UserField;
import ca.fun.simplyspend.exception.InvalidUserPasswordException;
import ca.fun.simplyspend.validator.PasswordValidator;
import io.quarkus.logging.Log;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

/**
 * Default implementation of {@link PasswordValidator}
 */
@Singleton
public class DefaultPasswordValidator implements PasswordValidator {

    PasswordConfig passwordConfig;

    @Inject
    public DefaultPasswordValidator(PasswordConfig passwordConfig) {
        this.passwordConfig = passwordConfig;
    }

    @Override
    public CreateUserValidationResult.ValidationResult isValid(String password) {
        if (checkPasswordLength(password) &&
                checkPasswordContainsNumber(password) &&
                checkPasswordContainsCapitalLetter(password) &&
                checkPasswordContainsSpecialCharacter(password)) {
            return new CreateUserValidationResult.ValidationResult(
                    UserField.PASSWORD,
                    "",
                    true
            );
        }
        return new CreateUserValidationResult.ValidationResult(
                UserField.PASSWORD,
                "Password must be between " + passwordConfig.getMinLength() + " and " + passwordConfig.getMaxLength() + " characters long, " +
                        "contain at least one number, one capital letter and one special character",
                false
        );
    }

    private boolean checkPasswordLength(String password) {
        boolean b = password.length() >= passwordConfig.getMinLength() && password.length() <= passwordConfig.getMaxLength();
        Log.info("Password length is " + password.length() + " and is " + (b ? "valid" : "invalid"));
        return b;
    }

    private boolean checkPasswordContainsNumber(String password) {
        boolean matches = password.matches(".*\\d.*");
        Log.info("Password contains number is " + matches);
        return matches;
    }

    private boolean checkPasswordContainsCapitalLetter(String password) {
        boolean matches = password.matches(".*[A-Z].*");
        Log.info("Password contains capital letter is " + matches);
        return matches;
    }

    private boolean checkPasswordContainsSpecialCharacter(String password) {
        String regex = ".*[" + passwordConfig.getSpecialCharacters() + "].*";
        boolean matches = password.matches(regex);
        Log.info("Password contains special character is " + matches);
        return matches;
    }

}

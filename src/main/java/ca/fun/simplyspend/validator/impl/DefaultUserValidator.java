package ca.fun.simplyspend.validator.impl;

import ca.fun.simplyspend.data.CreateUserRequest;
import ca.fun.simplyspend.data.CreateUserValidationResult;
import ca.fun.simplyspend.data.PasswordConfig;
import ca.fun.simplyspend.validator.EmailValidator;
import ca.fun.simplyspend.validator.PasswordValidator;
import ca.fun.simplyspend.validator.UserValidator;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import java.util.List;

@Singleton
public class DefaultUserValidator implements UserValidator {


    final EmailValidator emailValidator;

    final PasswordValidator passwordValidator;


    @Inject
    public DefaultUserValidator(EmailValidator emailValidator,
                                PasswordValidator passwordValidator) {
        this.emailValidator = emailValidator;
        this.passwordValidator = passwordValidator;
    }

    /**
     * rules:
     * Username should be a valid email identifier.
     * Username should be unique.
     * Password should be a minimum of 8 characters and a maximum of 16 characters.
     * Check for a minimum of one number, one capital letter, and one special character.
     *
     * @param createUserRequest createUserRequest
     * @return Uni<Boolean>
     */
    @Override
    public Uni<CreateUserValidationResult> validate(CreateUserRequest createUserRequest) {

        // valid email identifier.
        CreateUserValidationResult.ValidationResult isValidEmail = emailValidator.isValid(createUserRequest.emailId());

        // unique username -> let's assume it's unique for now. the caller of this service which is
        // the userService will check if the username is unique -
        // because its the one that has access to the database.
        boolean isUniqueUsername = true;

        // password validation
        CreateUserValidationResult.ValidationResult isValidPassword = passwordValidator.isValid(createUserRequest.password());


        if (isValidEmail.isValid() && isUniqueUsername && isValidPassword.isValid()) {
            return Uni.createFrom().item(new CreateUserValidationResult(List.of(),
                    true));
        }

        return Uni.createFrom().item(new CreateUserValidationResult(List.of(isValidEmail, isValidPassword),
                false));
    }

    @Override
    public Uni<Boolean> validate2(CreateUserRequest createUserRequest) {

        return null;
    }
}

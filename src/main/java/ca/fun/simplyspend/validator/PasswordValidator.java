package ca.fun.simplyspend.validator;

import ca.fun.simplyspend.data.CreateUserValidationResult;
import io.smallrye.mutiny.Uni;

public interface PasswordValidator{
    CreateUserValidationResult.ValidationResult isValid(String password);
}

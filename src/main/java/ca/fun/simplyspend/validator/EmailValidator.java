package ca.fun.simplyspend.validator;

import ca.fun.simplyspend.data.CreateUserValidationResult;

public interface EmailValidator{

    CreateUserValidationResult.ValidationResult isValid(String email);

}

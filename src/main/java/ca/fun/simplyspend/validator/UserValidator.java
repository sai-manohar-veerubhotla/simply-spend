package ca.fun.simplyspend.validator;

import ca.fun.simplyspend.data.CreateUserRequest;
import ca.fun.simplyspend.data.CreateUserValidationResult;
import io.smallrye.mutiny.Uni;

public interface UserValidator {
    Uni<CreateUserValidationResult> validate(CreateUserRequest createUserRequest);

    Uni<Boolean> validate2(CreateUserRequest createUserRequest);

}

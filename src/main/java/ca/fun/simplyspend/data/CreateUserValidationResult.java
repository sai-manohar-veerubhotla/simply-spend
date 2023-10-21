package ca.fun.simplyspend.data;

import java.util.List;

public record CreateUserValidationResult(List<ValidationResult> validationResults, boolean isValid) {

    public record ValidationResult(UserField userField, String errorMessage, boolean isValid) {

    }
}

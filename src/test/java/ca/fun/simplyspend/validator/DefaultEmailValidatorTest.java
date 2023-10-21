package ca.fun.simplyspend.validator;

import ca.fun.simplyspend.data.CreateUserValidationResult;
import ca.fun.simplyspend.validator.impl.DefaultEmailValidator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class DefaultEmailValidatorTest {


    static EmailValidator defaultEmailValidator;

    @BeforeAll
    public static void setup() {
        defaultEmailValidator = new DefaultEmailValidator();
    }


    @Test
    public void testValidEmail() {
        CreateUserValidationResult.ValidationResult validationResult =
                defaultEmailValidator.isValid("sai@example.com");
    }

    @Test
    public void testInValidEmail() {
        CreateUserValidationResult.ValidationResult validationResult =
                defaultEmailValidator.isValid("saiexample.com");
    }

}

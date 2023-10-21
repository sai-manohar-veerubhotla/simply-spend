package ca.fun.simplyspend.validator;

import ca.fun.simplyspend.data.CreateUserValidationResult;
import ca.fun.simplyspend.data.PasswordConfig;
import ca.fun.simplyspend.validator.impl.DefaultPasswordValidator;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class DefaultPasswordValidatorTest {

    static PasswordValidator defaultPasswordValidator;


    @BeforeAll
    public static void setup() {
        PasswordConfig passwordConfig = new PasswordConfig(
                8,
                16,
                1,
                1,
                1,
                "@#$%^&+="
        );
        defaultPasswordValidator = new DefaultPasswordValidator(passwordConfig);
    }

    @Test
    public void testValidPassword() {
        CreateUserValidationResult.ValidationResult validationResult = defaultPasswordValidator.isValid("password");
        Assertions.assertFalse(validationResult.isValid());
    }

    @Test
    public void testInValidPassword() {
        CreateUserValidationResult.ValidationResult validationResult = defaultPasswordValidator.isValid("Sai@Manohar123");
        Assertions.assertTrue(validationResult.isValid());
    }
}

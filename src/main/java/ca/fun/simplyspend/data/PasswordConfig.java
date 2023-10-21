package ca.fun.simplyspend.data;

import jakarta.inject.Singleton;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.eclipse.microprofile.config.inject.ConfigProperty;

/**
 * Password configuration.
 * Password should be a minimum of 8 characters and a maximum of 16 characters.
 * Check for a minimum of one number, one capital letter, and one special character.
 * user.password.minLength=8
 * user.password.maxLength=16
 * user.password.numberOfDigits=1
 * user.password.numberOfCapitalLetters=1
 * user.password.numberOfSpecialCharacters=1
 * user.password.specialCharacters="!#$%&'*+-/=?^_`{|"
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Singleton
public class PasswordConfig {
    @ConfigProperty(name = "user.password.minLength")
    int minLength;
    @ConfigProperty(name = "user.password.maxLength")
    int maxLength;
    @ConfigProperty(name = "user.password.numberOfDigits")
    int numberOfDigits;
    @ConfigProperty(name = "user.password.numberOfCapitalLetters")
    int numberOfCapitalLetters;
    @ConfigProperty(name = "user.password.numberOfSpecialCharacters")
    int numberOfSpecialCharacters;
    @ConfigProperty(name = "user.password.specialCharacters")
    String specialCharacters;
}

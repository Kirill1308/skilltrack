package com.skilltrack.common.validation.annotation;

import com.skilltrack.common.validation.PasswordValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to validate that a password meets security requirements:
 * - At least 8 characters long
 * - Contains at least one digit
 * - Contains at least one lowercase letter
 * - Contains at least one uppercase letter
 * - Contains at least one special character
 * - No whitespace
 */
@Documented
@Constraint(validatedBy = PasswordValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidPassword {
    String message() default "Password must be at least 8 characters long and contain at least one digit, one lowercase, one uppercase, and one special character";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};


    int minLength() default 8;

    boolean requireDigit() default true;

    boolean requireLowercase() default true;

    boolean requireUppercase() default true;

    boolean requireSpecialChar() default true;

    boolean disallowWhitespace() default true;
}

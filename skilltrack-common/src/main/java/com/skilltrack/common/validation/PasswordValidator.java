package com.skilltrack.common.validation;

import com.skilltrack.common.validation.annotation.ValidPassword;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<ValidPassword, String> {

    private int minLength;
    private boolean requireDigit;
    private boolean requireLowercase;
    private boolean requireUppercase;
    private boolean requireSpecialChar;
    private boolean disallowWhitespace;

    @Override
    public void initialize(ValidPassword constraintAnnotation) {
        this.minLength = constraintAnnotation.minLength();
        this.requireDigit = constraintAnnotation.requireDigit();
        this.requireLowercase = constraintAnnotation.requireLowercase();
        this.requireUppercase = constraintAnnotation.requireUppercase();
        this.requireSpecialChar = constraintAnnotation.requireSpecialChar();
        this.disallowWhitespace = constraintAnnotation.disallowWhitespace();
    }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        // Null check - we'll let @NotBlank handle this if needed
        if (password == null) {
            return true;
        }

        boolean valid = true;

        // Create a custom error message based on which validations failed
        context.disableDefaultConstraintViolation();
        StringBuilder messageBuilder = new StringBuilder();

        // Check length
        if (password.length() < minLength) {
            messageBuilder.append("Password must be at least ").append(minLength).append(" characters long. ");
            valid = false;
        }

        // Check for digit
        if (requireDigit && !password.matches(".*\\d.*")) {
            messageBuilder.append("Password must contain at least one digit. ");
            valid = false;
        }

        // Check for lowercase
        if (requireLowercase && !password.matches(".*[a-z].*")) {
            messageBuilder.append("Password must contain at least one lowercase letter. ");
            valid = false;
        }

        // Check for uppercase
        if (requireUppercase && !password.matches(".*[A-Z].*")) {
            messageBuilder.append("Password must contain at least one uppercase letter. ");
            valid = false;
        }

        // Check for special character
        if (requireSpecialChar && !password.matches(".*[@#$%^&+=].*")) {
            messageBuilder.append("Password must contain at least one special character (@#$%^&+=). ");
            valid = false;
        }

        // Check for whitespace
        if (disallowWhitespace && password.matches(".*\\s.*")) {
            messageBuilder.append("Password must not contain whitespace. ");
            valid = false;
        }

        // If validation failed, add custom message
        if (!valid) {
            context.buildConstraintViolationWithTemplate(messageBuilder.toString().trim())
                    .addConstraintViolation();
        }

        return valid;
    }
}

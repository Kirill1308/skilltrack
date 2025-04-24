package com.skilltrack.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    // Authentication & Authorization
    INVALID_CREDENTIALS(HttpStatus.UNAUTHORIZED, "Invalid username or password"),
    ACCOUNT_LOCKED(HttpStatus.UNAUTHORIZED, "Your account has been locked. Please contact support"),
    ACCOUNT_DISABLED(HttpStatus.UNAUTHORIZED, "Your account is disabled"),
    SESSION_EXPIRED(HttpStatus.UNAUTHORIZED, "Your session has expired. Please log in again"),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "Invalid or expired token"),
    INSUFFICIENT_PERMISSIONS(HttpStatus.FORBIDDEN, "You don't have permission to perform this action"),

    // User Management
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "User not found"),
    USER_ALREADY_EXISTS(HttpStatus.CONFLICT, "A user with this email already exists"),
    USER_INACTIVE(HttpStatus.FORBIDDEN, "User account is inactive"),
    INVALID_EMAIL_FORMAT(HttpStatus.BAD_REQUEST, "Invalid email format"),
    INVALID_PASSWORD_FORMAT(HttpStatus.BAD_REQUEST, "Password doesn't meet requirements"),
    EMAIL_VERIFICATION_REQUIRED(HttpStatus.FORBIDDEN, "Email verification required to access this resource"),

    // Resource Access
    RESOURCE_NOT_FOUND(HttpStatus.NOT_FOUND, "The requested resource was not found"),
    RESOURCE_ALREADY_EXISTS(HttpStatus.CONFLICT, "A resource with this identifier already exists"),
    RESOURCE_ACCESS_DENIED(HttpStatus.FORBIDDEN, "You don't have access to this resource"),
    RESOURCE_LOCKED(HttpStatus.CONFLICT, "This resource is currently locked by another user"),

    // Input Validation
    VALIDATION_FAILED(HttpStatus.BAD_REQUEST, "Input validation failed"),
    MISSING_REQUIRED_FIELD(HttpStatus.BAD_REQUEST, "Required field is missing"),
    MISSING_REQUIRED_PARAMETER(HttpStatus.BAD_REQUEST, "Required parameter is missing"),
    INVALID_PARAMETER_TYPE(HttpStatus.BAD_REQUEST, "Invalid parameter type"),
    INVALID_INPUT_FORMAT(HttpStatus.BAD_REQUEST, "Input format is invalid"),
    INVALID_DATE_RANGE(HttpStatus.BAD_REQUEST, "Invalid date range"),
    INPUT_TOO_LARGE(HttpStatus.BAD_REQUEST, "Input exceeds maximum allowed size"),
    INVALID_FILE_TYPE(HttpStatus.BAD_REQUEST, "File type not supported"),

    // Business Logic
    BUSINESS_RULE_VIOLATION(HttpStatus.CONFLICT, "Operation violated a business rule"),
    INSUFFICIENT_FUNDS(HttpStatus.CONFLICT, "Insufficient funds to complete the transaction"),
    QUOTA_EXCEEDED(HttpStatus.TOO_MANY_REQUESTS, "Your usage quota has been exceeded"),
    RATE_LIMIT_EXCEEDED(HttpStatus.TOO_MANY_REQUESTS, "Rate limit exceeded. Please try again later"),
    OPERATION_NOT_ALLOWED(HttpStatus.CONFLICT, "Operation not allowed in the current state"),

    // External Services
    EXTERNAL_SERVICE_UNAVAILABLE(HttpStatus.SERVICE_UNAVAILABLE, "External service is currently unavailable"),
    EXTERNAL_SERVICE_TIMEOUT(HttpStatus.GATEWAY_TIMEOUT, "External service timed out"),
    EXTERNAL_SERVICE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Error occurred in external service"),

    // Data Consistency
    DATA_INTEGRITY_VIOLATION(HttpStatus.CONFLICT, "Operation would violate data integrity"),
    STALE_DATA(HttpStatus.CONFLICT, "Data has been modified by another user. Please refresh"),
    INVALID_STATE_TRANSITION(HttpStatus.CONFLICT, "Invalid state transition requested"),

    // System Issues
    INTERNAL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred"),
    DATABASE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Database operation failed"),
    FILE_STORAGE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "File storage operation failed"),
    SCHEDULED_MAINTENANCE(HttpStatus.SERVICE_UNAVAILABLE, "System is undergoing scheduled maintenance");

    private final HttpStatus status;
    private final String message;
}

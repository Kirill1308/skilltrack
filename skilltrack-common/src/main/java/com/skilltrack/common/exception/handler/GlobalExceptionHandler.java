package com.skilltrack.common.exception.handler;

import com.skilltrack.common.exception.ApiError;
import com.skilltrack.common.exception.BaseException;
import com.skilltrack.common.exception.ErrorCode;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ApiError> handleBaseException(BaseException ex, HttpServletRequest request) {
        ApiError apiError = ApiError.builder()
                .path(request.getRequestURI())
                .message(ex.getMessage())
                .statusCode(ex.getErrorCode().getStatus().value())
                .statusName(ex.getErrorCode().name())
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.status(ex.getErrorCode().getStatus()).body(apiError);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidationException(
            MethodArgumentNotValidException ex,
            HttpServletRequest request
    ) {
        List<String> errors = new ArrayList<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(error -> errors.add(error.getField() + ": " + error.getDefaultMessage()));

        ApiError apiError = ApiError.builder()
                .path(request.getRequestURI())
                .message("Validation failed")
                .statusCode(ErrorCode.VALIDATION_FAILED.getStatus().value())
                .statusName(ErrorCode.VALIDATION_FAILED.getStatus().name())
                .timestamp(LocalDateTime.now())
                .errors(errors)
                .build();

        return ResponseEntity.badRequest().body(apiError);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiError> handleConstraintViolation(
            ConstraintViolationException ex,
            HttpServletRequest request
    ) {
        List<String> errors = new ArrayList<>();
        ex.getConstraintViolations()
                .forEach(violation -> errors.add(violation.getPropertyPath() + ": " + violation.getMessage()));

        ApiError apiError = ApiError.builder()
                .path(request.getRequestURI())
                .message("Validation failed")
                .statusCode(ErrorCode.VALIDATION_FAILED.getStatus().value())
                .statusName(ErrorCode.VALIDATION_FAILED.getStatus().name())
                .timestamp(LocalDateTime.now())
                .errors(errors)
                .build();

        return ResponseEntity.badRequest().body(apiError);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ApiError> handleMissingParams(
            MissingServletRequestParameterException ex,
            HttpServletRequest request
    ) {
        ApiError apiError = ApiError.builder()
                .path(request.getRequestURI())
                .message("Missing required parameter: " + ex.getParameterName())
                .statusCode(ErrorCode.MISSING_REQUIRED_PARAMETER.getStatus().value())
                .statusName(ErrorCode.MISSING_REQUIRED_PARAMETER.getStatus().name())
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.badRequest().body(apiError);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiError> handleTypeMismatch(
            MethodArgumentTypeMismatchException ex,
            HttpServletRequest request
    ) {
        String message = String.format(
                "Parameter '%s' should be of type %s",
                ex.getName(),
                Objects.requireNonNull(ex.getRequiredType()).getSimpleName()
        );

        ApiError apiError = ApiError.builder()
                .path(request.getRequestURI())
                .message(message)
                .statusCode(ErrorCode.INVALID_PARAMETER_TYPE.getStatus().value())
                .statusName(ErrorCode.INVALID_PARAMETER_TYPE.getStatus().name())
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.badRequest().body(apiError);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleAllUncaughtException(
            Exception ex,
            HttpServletRequest request
    ) {
        ApiError apiError = ApiError.builder()
                .path(request.getRequestURI())
                .message(ex.getMessage())
                .statusCode(ErrorCode.INTERNAL_ERROR.getStatus().value())
                .statusName(ErrorCode.INTERNAL_ERROR.name())
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.internalServerError().body(apiError);
    }

}

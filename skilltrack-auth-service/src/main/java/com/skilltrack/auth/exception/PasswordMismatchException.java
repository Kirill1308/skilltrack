package com.skilltrack.auth.exception;

import com.skilltrack.common.exception.BaseException;
import com.skilltrack.common.exception.ErrorCode;

public class PasswordMismatchException extends BaseException {
    public PasswordMismatchException() {
        super(ErrorCode.VALIDATION_FAILED, "New password and confirm password do not match");
    }

    public PasswordMismatchException(String message) {
        super(ErrorCode.VALIDATION_FAILED, message);
    }
}

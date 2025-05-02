package com.skilltrack.auth.exception;

import com.skilltrack.common.exception.BaseException;
import com.skilltrack.common.exception.ErrorCode;

public class InvalidCredentialsException extends BaseException {
    public InvalidCredentialsException(String message) {
        super(ErrorCode.INVALID_CREDENTIALS, message);
    }
}

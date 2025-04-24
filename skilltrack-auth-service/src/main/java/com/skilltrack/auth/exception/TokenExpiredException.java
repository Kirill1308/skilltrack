package com.skilltrack.auth.exception;

import com.skilltrack.common.exception.BaseException;
import com.skilltrack.common.exception.ErrorCode;

public class TokenExpiredException extends BaseException {
    public TokenExpiredException(String message) {
        super(ErrorCode.INVALID_TOKEN, message);
    }
}

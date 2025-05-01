package com.skilltrack.auth.exception;

import com.skilltrack.common.exception.BaseException;
import com.skilltrack.common.exception.ErrorCode;

import java.util.UUID;

public class UserNotFoundException extends BaseException {
    private static final String NOT_FOUND_WITH_ID = "User not found with id: %s";

    public UserNotFoundException(String message) {
        super(ErrorCode.USER_NOT_FOUND, message);
    }

    public UserNotFoundException(UUID userId) {
        super(ErrorCode.USER_NOT_FOUND, String.format(NOT_FOUND_WITH_ID, userId));
    }

}

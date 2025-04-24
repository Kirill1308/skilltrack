package com.skilltrack.user.exception;

import com.skilltrack.common.exception.BaseException;
import com.skilltrack.common.exception.ErrorCode;

import java.util.UUID;

public class UserProfileNotFoundException extends BaseException {
    private static final String NOT_FOUND_WITH_ID = "User Profile not found with id: %s";

    public UserProfileNotFoundException(UUID userId) {
        super(ErrorCode.USER_NOT_FOUND, String.format(NOT_FOUND_WITH_ID, userId));
    }

}

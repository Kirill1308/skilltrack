package com.skilltrack.auth.exception;

import com.skilltrack.common.exception.BaseException;
import com.skilltrack.common.exception.ErrorCode;

public class UserInactiveException extends BaseException {
    public UserInactiveException() {
        super(ErrorCode.USER_INACTIVE);
    }
}

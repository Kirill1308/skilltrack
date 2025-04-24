package com.skilltrack.user.exception;

import com.skilltrack.common.exception.BaseException;
import com.skilltrack.common.exception.ErrorCode;

public class DepartmentNotFoundException extends BaseException {
    public DepartmentNotFoundException(String message) {
        super(ErrorCode.RESOURCE_NOT_FOUND, message);
    }
}

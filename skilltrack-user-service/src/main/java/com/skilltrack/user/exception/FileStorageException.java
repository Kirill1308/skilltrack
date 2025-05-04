package com.skilltrack.user.exception;

import com.skilltrack.common.exception.BaseException;
import com.skilltrack.common.exception.ErrorCode;

public class FileStorageException extends BaseException {
    public FileStorageException(String message) {
        super(ErrorCode.FILE_STORAGE_ERROR, message);
    }

    public FileStorageException(String message, Throwable cause) {
        super(ErrorCode.FILE_STORAGE_ERROR, message, cause);
    }
}

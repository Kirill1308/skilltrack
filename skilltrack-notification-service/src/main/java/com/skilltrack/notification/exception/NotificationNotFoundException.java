package com.skilltrack.notification.exception;

import com.skilltrack.common.exception.BaseException;
import com.skilltrack.common.exception.ErrorCode;

import java.util.UUID;

public class NotificationNotFoundException extends BaseException {

    private static final String ID_MESSAGE = "Notification not found with id: %s";

    public NotificationNotFoundException(String message) {
        super(ErrorCode.RESOURCE_NOT_FOUND, message);
    }

    public NotificationNotFoundException(UUID id) {
        super(ErrorCode.RESOURCE_NOT_FOUND, String.format(ID_MESSAGE, id));
    }
}

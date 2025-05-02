package com.skilltrack.notification.exception;

import com.skilltrack.common.exception.BaseException;
import com.skilltrack.common.exception.ErrorCode;

public class NotificationNotFoundException extends BaseException {

    private static final String ID_MESSAGE = "Notification not found with id: %s";

    public NotificationNotFoundException(String id) {
        super(ErrorCode.RESOURCE_NOT_FOUND, String.format(ID_MESSAGE, id));
    }
}

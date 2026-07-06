package com.collab.workspace_service.common.exception;

import com.collab.workspace_service.common.error.ErrorCode;

import java.util.Objects;

public class CollabException extends RuntimeException {

    private final ErrorCode errorCode;

    public CollabException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = Objects.requireNonNull(errorCode, "Error code must not be null");
    }

    public ErrorCode errorCode() {
        return errorCode;
    }
}
package com.collab.workspace_service.common.error;

public enum WorkspaceErrorCode implements ErrorCode {

    WORKSPACE_NOT_FOUND("WORKSPACE_NOT_FOUND", "Workspace not found"),
    INVALID_WORKSPACE_ID("INVALID_WORKSPACE_ID", "Invalid workspace id"),
    INVALID_PAGINATION("INVALID_PAGINATION", "Invalid pagination parameters"),
    VALIDATION_ERROR("VALIDATION_ERROR", "Validation error"),
    TECHNICAL_EXCEPTION("TECHNICAL_EXCEPTION", "Technical exception");

    private final String code;
    private final String defaultMessage;

    WorkspaceErrorCode(String code, String defaultMessage) {
        this.code = code;
        this.defaultMessage = defaultMessage;
    }

    @Override
    public String code() {
        return code;
    }

    @Override
    public String defaultMessage() {
        return defaultMessage;
    }
}
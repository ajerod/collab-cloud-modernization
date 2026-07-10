package com.collab.workspace_service.application.exception;

public class WorkspaceAccessDeniedException extends RuntimeException {

    public WorkspaceAccessDeniedException(String workspaceId) {
        super("Access denied to workspace: " + workspaceId);
    }
}
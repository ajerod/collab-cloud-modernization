package com.collab.workspace_service.application.exception;

public class WorkspaceNotFoundException extends RuntimeException {

    public WorkspaceNotFoundException(String workspaceId) {
        super("Workspace not found with id: " + workspaceId);
    }
}
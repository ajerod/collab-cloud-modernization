package com.collab.workspace_service.application.exception;

import com.collab.workspace_service.common.error.WorkspaceErrorCode;
import com.collab.workspace_service.common.exception.CollabException;

public class WorkspaceNotFoundException extends CollabException {

    public WorkspaceNotFoundException(String workspaceId) {
        super(
                WorkspaceErrorCode.WORKSPACE_NOT_FOUND,
                "Workspace not found with id: " + workspaceId
        );
    }
}
package com.collab.workspace_service.application.port.in;

import com.collab.workspace_service.domain.model.Workspace;

public interface CreateWorkspaceUseCase {

    Workspace createWorkspace(CreateWorkspaceCommand command);
}
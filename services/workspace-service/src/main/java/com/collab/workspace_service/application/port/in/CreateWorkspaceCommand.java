package com.collab.workspace_service.application.port.in;

public record CreateWorkspaceCommand(
        String name,
        String ownerId
) {
}
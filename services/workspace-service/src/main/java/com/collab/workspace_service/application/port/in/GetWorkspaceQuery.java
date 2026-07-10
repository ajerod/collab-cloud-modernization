package com.collab.workspace_service.application.port.in;

public record GetWorkspaceQuery(
        String workspaceId,
        String authenticatedUserId
) {
}
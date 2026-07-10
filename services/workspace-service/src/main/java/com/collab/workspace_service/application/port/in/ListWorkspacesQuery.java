package com.collab.workspace_service.application.port.in;

public record ListWorkspacesQuery(
        String authenticatedUserId,
        int page,
        int size
) {
}
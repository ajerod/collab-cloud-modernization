package com.collab.workspace_service.application.port.in;

public record ListWorkspacesQuery(
        int page,
        int size
) {
}
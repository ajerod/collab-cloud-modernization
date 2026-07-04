package com.collab.workspace_service.adapter.in.web.response;

import com.collab.workspace_service.domain.model.Workspace;

import java.time.Instant;

public record WorkspaceResponse(
        String id,
        String name,
        String ownerId,
        Instant createdAt
) {

    public static WorkspaceResponse from(Workspace workspace) {
        return new WorkspaceResponse(
                workspace.id().toString(),
                workspace.name(),
                workspace.ownerId(),
                workspace.createdAt()
        );
    }
}
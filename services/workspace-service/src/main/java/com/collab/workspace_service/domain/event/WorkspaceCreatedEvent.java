package com.collab.workspace_service.domain.event;

import com.collab.workspace_service.domain.model.Workspace;

import java.time.Instant;
import java.util.UUID;

public record WorkspaceCreatedEvent(
        String eventId,
        String eventType,
        String aggregateId,
        String workspaceName,
        String ownerId,
        Instant occurredAt
) implements DomainEvent {

    public static WorkspaceCreatedEvent from(Workspace workspace) {
        return new WorkspaceCreatedEvent(
                UUID.randomUUID().toString(),
                "WORKSPACE_CREATED",
                workspace.id().toString(),
                workspace.name(),
                workspace.ownerId(),
                Instant.now()
        );
    }
}
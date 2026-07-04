package com.collab.workspace_service.domain.model;

import java.util.Objects;
import java.util.UUID;

public record WorkspaceId(UUID value) {

    public WorkspaceId {
        Objects.requireNonNull(value, "Workspace id must not be null");
    }

    public static WorkspaceId newId() {
        return new WorkspaceId(UUID.randomUUID());
    }

    public static WorkspaceId from(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Workspace id must not be blank");
        }

        return new WorkspaceId(UUID.fromString(value));
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
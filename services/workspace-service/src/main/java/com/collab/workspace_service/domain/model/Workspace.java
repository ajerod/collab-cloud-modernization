package com.collab.workspace_service.domain.model;

import java.time.Instant;
import java.util.Objects;

public class Workspace {

    private final WorkspaceId id;
    private final String name;
    private final String ownerId;
    private final Instant createdAt;

    private Workspace(
            WorkspaceId id,
            String name,
            String ownerId,
            Instant createdAt
    ) {
        this.id = Objects.requireNonNull(id, "Workspace id must not be null");
        this.name = validateName(name);
        this.ownerId = validateOwnerId(ownerId);
        this.createdAt = Objects.requireNonNull(createdAt, "Creation date must not be null");
    }

    public static Workspace create(String name, String ownerId) {
        return new Workspace(
                WorkspaceId.newId(),
                name,
                ownerId,
                Instant.now()
        );
    }

    public static Workspace rehydrate(
            WorkspaceId id,
            String name,
            String ownerId,
            Instant createdAt
    ) {
        return new Workspace(id, name, ownerId, createdAt);
    }

    public WorkspaceId id() {
        return id;
    }

    public String name() {
        return name;
    }

    public String ownerId() {
        return ownerId;
    }

    public Instant createdAt() {
        return createdAt;
    }

    public WorkspaceId getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    private static String validateName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Workspace name must not be blank");
        }

        return name.trim();
    }

    private static String validateOwnerId(String ownerId) {
        if (ownerId == null || ownerId.isBlank()) {
            throw new IllegalArgumentException("Owner id must not be blank");
        }

        return ownerId.trim();
    }
}
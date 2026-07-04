package com.collab.workspace_service.domain.model;

import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class WorkspaceTest {

    @Test
    void shouldCreateWorkspaceWithValidData() {
        Workspace workspace = Workspace.create("Architecture Workspace", "user-001");

        assertNotNull(workspace.id());
        assertEquals("Architecture Workspace", workspace.name());
        assertEquals("user-001", workspace.ownerId());
        assertNotNull(workspace.createdAt());
    }

    @Test
    void shouldTrimWorkspaceNameAndOwnerId() {
        Workspace workspace = Workspace.create("  Architecture Workspace  ", "  user-001  ");

        assertEquals("Architecture Workspace", workspace.name());
        assertEquals("user-001", workspace.ownerId());
    }

    @Test
    void shouldRejectBlankWorkspaceName() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> Workspace.create(" ", "user-001")
        );

        assertEquals("Workspace name must not be blank", exception.getMessage());
    }

    @Test
    void shouldRejectBlankOwnerId() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> Workspace.create("Architecture Workspace", " ")
        );

        assertEquals("Owner id must not be blank", exception.getMessage());
    }

    @Test
    void shouldRehydrateExistingWorkspace() {
        WorkspaceId workspaceId = new WorkspaceId(UUID.randomUUID());
        Instant createdAt = Instant.parse("2026-07-04T10:00:00Z");

        Workspace workspace = Workspace.rehydrate(
                workspaceId,
                "Existing Workspace",
                "user-001",
                createdAt
        );

        assertEquals(workspaceId, workspace.id());
        assertEquals("Existing Workspace", workspace.name());
        assertEquals("user-001", workspace.ownerId());
        assertEquals(createdAt, workspace.createdAt());
    }

    @Test
    void shouldRejectNullWorkspaceIdWhenRehydrating() {
        NullPointerException exception = assertThrows(
                NullPointerException.class,
                () -> Workspace.rehydrate(
                        null,
                        "Existing Workspace",
                        "user-001",
                        Instant.now()
                )
        );

        assertEquals("Workspace id must not be null", exception.getMessage());
    }
}
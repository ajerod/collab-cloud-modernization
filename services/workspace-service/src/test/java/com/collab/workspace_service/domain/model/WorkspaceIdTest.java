package com.collab.workspace_service.domain.model;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class WorkspaceIdTest {

    @Test
    void shouldCreateNewWorkspaceId() {
        WorkspaceId workspaceId = WorkspaceId.newId();

        assertNotNull(workspaceId);
        assertNotNull(workspaceId.value());
    }

    @Test
    void shouldCreateWorkspaceIdFromString() {
        UUID uuid = UUID.randomUUID();

        WorkspaceId workspaceId = WorkspaceId.from(uuid.toString());

        assertEquals(uuid, workspaceId.value());
    }

    @Test
    void shouldRejectBlankValue() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> WorkspaceId.from(" ")
        );

        assertEquals("Workspace id must not be blank", exception.getMessage());
    }

    @Test
    void shouldRejectNullValue() {
        NullPointerException exception = assertThrows(
                NullPointerException.class,
                () -> new WorkspaceId(null)
        );

        assertEquals("Workspace id must not be null", exception.getMessage());
    }

    @Test
    void shouldReturnStringValue() {
        UUID uuid = UUID.randomUUID();
        WorkspaceId workspaceId = new WorkspaceId(uuid);

        assertEquals(uuid.toString(), workspaceId.toString());
    }
}
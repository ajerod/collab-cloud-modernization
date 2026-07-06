package com.collab.workspace_service.application.service;

import com.collab.workspace_service.application.exception.WorkspaceNotFoundException;
import com.collab.workspace_service.application.port.in.GetWorkspaceQuery;
import com.collab.workspace_service.application.port.out.WorkspaceRepositoryPort;
import com.collab.workspace_service.domain.model.Workspace;
import com.collab.workspace_service.domain.model.WorkspaceId;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class GetWorkspaceServiceTest {

    @Test
    void shouldGetWorkspaceWhenItExists() {
        Workspace workspace = Workspace.create("Architecture Workspace", "user-001");
        FakeWorkspaceRepositoryPort workspaceRepositoryPort = new FakeWorkspaceRepositoryPort(workspace);

        GetWorkspaceService service = new GetWorkspaceService(workspaceRepositoryPort);

        Workspace result = service.getWorkspace(
                new GetWorkspaceQuery(workspace.id().toString())
        );

        assertEquals(workspace, result);
        assertTrue(workspaceRepositoryPort.findByIdCalled);
    }

    @Test
    void shouldThrowExceptionWhenWorkspaceDoesNotExist() {
        FakeWorkspaceRepositoryPort workspaceRepositoryPort = new FakeWorkspaceRepositoryPort(null);
        GetWorkspaceService service = new GetWorkspaceService(workspaceRepositoryPort);

        String missingWorkspaceId = UUID.randomUUID().toString();

        WorkspaceNotFoundException exception = assertThrows(
                WorkspaceNotFoundException.class,
                () -> service.getWorkspace(new GetWorkspaceQuery(missingWorkspaceId))
        );

        assertEquals(
                "Workspace not found with id: " + missingWorkspaceId,
                exception.getMessage()
        );
    }

    @Test
    void shouldRejectNullQuery() {
        GetWorkspaceService service = new GetWorkspaceService(
                new FakeWorkspaceRepositoryPort(null)
        );

        NullPointerException exception = assertThrows(
                NullPointerException.class,
                () -> service.getWorkspace(null)
        );

        assertEquals("Get workspace query must not be null", exception.getMessage());
    }

    @Test
    void shouldRejectInvalidWorkspaceId() {
        GetWorkspaceService service = new GetWorkspaceService(
                new FakeWorkspaceRepositoryPort(null)
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> service.getWorkspace(new GetWorkspaceQuery("not-a-uuid"))
        );
    }

    @Test
    void shouldRejectNullRepositoryPort() {
        NullPointerException exception = assertThrows(
                NullPointerException.class,
                () -> new GetWorkspaceService(null)
        );

        assertEquals("Workspace repository port must not be null", exception.getMessage());
    }

    private static class FakeWorkspaceRepositoryPort implements WorkspaceRepositoryPort {

        private final Workspace workspace;
        private boolean findByIdCalled;

        private FakeWorkspaceRepositoryPort(Workspace workspace) {
            this.workspace = workspace;
        }

        @Override
        public Workspace save(Workspace workspace) {
            return workspace;
        }

        @Override
        public Optional<Workspace> findById(WorkspaceId workspaceId) {
            this.findByIdCalled = true;
            return Optional.ofNullable(workspace);
        }
    }
}
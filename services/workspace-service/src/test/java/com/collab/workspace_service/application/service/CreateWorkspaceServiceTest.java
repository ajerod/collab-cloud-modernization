package com.collab.workspace_service.application.service;

import com.collab.workspace_service.application.model.PagedResult;
import com.collab.workspace_service.application.port.in.CreateWorkspaceCommand;
import com.collab.workspace_service.application.port.out.DomainEventPublisherPort;
import com.collab.workspace_service.application.port.out.WorkspaceRepositoryPort;
import com.collab.workspace_service.domain.event.DomainEvent;
import com.collab.workspace_service.domain.event.WorkspaceCreatedEvent;
import com.collab.workspace_service.domain.model.Workspace;
import com.collab.workspace_service.domain.model.WorkspaceId;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class CreateWorkspaceServiceTest {

    @Test
    void shouldCreateWorkspaceAndPublishEvent() {
        FakeWorkspaceRepositoryPort workspaceRepositoryPort = new FakeWorkspaceRepositoryPort();
        FakeDomainEventPublisherPort domainEventPublisherPort = new FakeDomainEventPublisherPort();

        CreateWorkspaceService service = new CreateWorkspaceService(
                workspaceRepositoryPort,
                domainEventPublisherPort
        );

        Workspace workspace = service.createWorkspace(
                new CreateWorkspaceCommand("Architecture Workspace", "user-001")
        );

        assertNotNull(workspace);
        assertNotNull(workspace.id());
        assertEquals("Architecture Workspace", workspace.name());
        assertEquals("user-001", workspace.ownerId());

        assertTrue(workspaceRepositoryPort.saveCalled);
        assertEquals(workspace, workspaceRepositoryPort.savedWorkspace);

        assertTrue(domainEventPublisherPort.publishCalled);
        assertNotNull(domainEventPublisherPort.publishedEvent);
        assertInstanceOf(WorkspaceCreatedEvent.class, domainEventPublisherPort.publishedEvent);
        assertEquals("WORKSPACE_CREATED", domainEventPublisherPort.publishedEvent.eventType());
        assertEquals(workspace.id().toString(), domainEventPublisherPort.publishedEvent.aggregateId());
    }

    @Test
    void shouldRejectNullCommand() {
        CreateWorkspaceService service = new CreateWorkspaceService(
                new FakeWorkspaceRepositoryPort(),
                new FakeDomainEventPublisherPort()
        );

        NullPointerException exception = assertThrows(
                NullPointerException.class,
                () -> service.createWorkspace(null)
        );

        assertEquals("Create workspace command must not be null", exception.getMessage());
    }

    @Test
    void shouldRejectNullWorkspaceRepositoryPort() {
        NullPointerException exception = assertThrows(
                NullPointerException.class,
                () -> new CreateWorkspaceService(null, new FakeDomainEventPublisherPort())
        );

        assertEquals("Workspace repository port must not be null", exception.getMessage());
    }

    @Test
    void shouldRejectNullDomainEventPublisherPort() {
        NullPointerException exception = assertThrows(
                NullPointerException.class,
                () -> new CreateWorkspaceService(new FakeWorkspaceRepositoryPort(), null)
        );

        assertEquals("Domain event publisher port must not be null", exception.getMessage());
    }

    private static class FakeWorkspaceRepositoryPort implements WorkspaceRepositoryPort {

        private boolean saveCalled;
        private Workspace savedWorkspace;

        @Override
        public Workspace save(Workspace workspace) {
            this.saveCalled = true;
            this.savedWorkspace = workspace;
            return workspace;
        }

        @Override
        public Optional<Workspace> findById(WorkspaceId workspaceId) {
            return Optional.empty();
        }

        @Override
        public PagedResult<Workspace> findAll(int page, int size) {
            return new PagedResult<>(
                    List.of(),
                    page,
                    size,
                    0,
                    0
            );
        }
    }

    private static class FakeDomainEventPublisherPort implements DomainEventPublisherPort {

        private boolean publishCalled;
        private DomainEvent publishedEvent;

        @Override
        public void publish(DomainEvent event) {
            this.publishCalled = true;
            this.publishedEvent = event;
        }
    }
}
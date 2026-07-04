package com.collab.workspace_service.application.service;

import com.collab.workspace_service.application.port.in.CreateWorkspaceCommand;
import com.collab.workspace_service.application.port.in.CreateWorkspaceUseCase;
import com.collab.workspace_service.application.port.out.DomainEventPublisherPort;
import com.collab.workspace_service.application.port.out.WorkspaceRepositoryPort;
import com.collab.workspace_service.domain.event.WorkspaceCreatedEvent;
import com.collab.workspace_service.domain.model.Workspace;

import java.util.Objects;

public class CreateWorkspaceService implements CreateWorkspaceUseCase {

    private final WorkspaceRepositoryPort workspaceRepositoryPort;
    private final DomainEventPublisherPort domainEventPublisherPort;

    public CreateWorkspaceService(
            WorkspaceRepositoryPort workspaceRepositoryPort,
            DomainEventPublisherPort domainEventPublisherPort
    ) {
        this.workspaceRepositoryPort = Objects.requireNonNull(
                workspaceRepositoryPort,
                "Workspace repository port must not be null"
        );
        this.domainEventPublisherPort = Objects.requireNonNull(
                domainEventPublisherPort,
                "Domain event publisher port must not be null"
        );
    }

    @Override
    public Workspace createWorkspace(CreateWorkspaceCommand command) {
        Objects.requireNonNull(command, "Create workspace command must not be null");

        Workspace workspace = Workspace.create(
                command.name(),
                command.ownerId()
        );

        Workspace savedWorkspace = workspaceRepositoryPort.save(workspace);

        domainEventPublisherPort.publish(
                WorkspaceCreatedEvent.from(savedWorkspace)
        );

        return savedWorkspace;
    }
}
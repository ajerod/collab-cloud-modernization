package com.collab.workspace_service.application.service;

import com.collab.workspace_service.application.port.in.CreateWorkspaceCommand;
import com.collab.workspace_service.application.port.in.CreateWorkspaceUseCase;
import com.collab.workspace_service.application.port.out.DomainEventPublisherPort;
import com.collab.workspace_service.application.port.out.WorkspaceRepositoryPort;
import com.collab.workspace_service.domain.event.WorkspaceCreatedEvent;
import com.collab.workspace_service.domain.model.Workspace;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

public class CreateWorkspaceService implements CreateWorkspaceUseCase {

    private static final Logger LOGGER = LoggerFactory.getLogger(CreateWorkspaceService.class);

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

        LOGGER.info(
                "Creating workspace: ownerId={}, workspaceName={}",
                command.ownerId(),
                command.name()
        );

        Workspace workspace = Workspace.create(
                command.name(),
                command.ownerId()
        );

        Workspace savedWorkspace = workspaceRepositoryPort.save(workspace);

        domainEventPublisherPort.publish(
                WorkspaceCreatedEvent.from(savedWorkspace)
        );

        LOGGER.info(
                "Workspace created successfully: workspaceId={}, ownerId={}",
                savedWorkspace.id(),
                savedWorkspace.ownerId()
        );

        return savedWorkspace;
    }
}
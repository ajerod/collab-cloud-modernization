package com.collab.workspace_service.application.service;

import com.collab.workspace_service.application.exception.WorkspaceNotFoundException;
import com.collab.workspace_service.application.port.in.GetWorkspaceQuery;
import com.collab.workspace_service.application.port.in.GetWorkspaceUseCase;
import com.collab.workspace_service.application.port.out.WorkspaceRepositoryPort;
import com.collab.workspace_service.domain.model.Workspace;
import com.collab.workspace_service.domain.model.WorkspaceId;

import java.util.Objects;

public class GetWorkspaceService implements GetWorkspaceUseCase {

    private final WorkspaceRepositoryPort workspaceRepositoryPort;

    public GetWorkspaceService(WorkspaceRepositoryPort workspaceRepositoryPort) {
        this.workspaceRepositoryPort = Objects.requireNonNull(
                workspaceRepositoryPort,
                "Workspace repository port must not be null"
        );
    }

    @Override
    public Workspace getWorkspace(GetWorkspaceQuery query) {
        Objects.requireNonNull(query, "Get workspace query must not be null");

        WorkspaceId workspaceId = WorkspaceId.from(query.workspaceId());

        return workspaceRepositoryPort
                .findById(workspaceId)
                .orElseThrow(() -> new WorkspaceNotFoundException(query.workspaceId()));
    }
}
package com.collab.workspace_service.adapter.out.persistence;

import com.collab.workspace_service.application.port.out.WorkspaceRepositoryPort;
import com.collab.workspace_service.domain.model.Workspace;
import com.collab.workspace_service.domain.model.WorkspaceId;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryWorkspaceRepositoryAdapter implements WorkspaceRepositoryPort {

    private final Map<WorkspaceId, Workspace> workspaces = new ConcurrentHashMap<>();

    @Override
    public Workspace save(Workspace workspace) {
        workspaces.put(workspace.id(), workspace);
        return workspace;
    }

    @Override
    public Optional<Workspace> findById(WorkspaceId workspaceId) {
        return Optional.ofNullable(workspaces.get(workspaceId));
    }
}
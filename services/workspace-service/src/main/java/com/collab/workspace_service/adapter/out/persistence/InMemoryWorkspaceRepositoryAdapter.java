package com.collab.workspace_service.adapter.out.persistence;

import com.collab.workspace_service.application.model.PagedResult;
import com.collab.workspace_service.application.port.out.WorkspaceRepositoryPort;
import com.collab.workspace_service.domain.model.Workspace;
import com.collab.workspace_service.domain.model.WorkspaceId;

import java.util.Comparator;
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

    @Override
    public PagedResult<Workspace> findAll(int page, int size) {
        var sortedWorkspaces = workspaces.values()
                .stream()
                .sorted(Comparator.comparing(Workspace::createdAt).reversed())
                .toList();

        int fromIndex = Math.min(page * size, sortedWorkspaces.size());
        int toIndex = Math.min(fromIndex + size, sortedWorkspaces.size());

        var items = sortedWorkspaces.subList(fromIndex, toIndex);

        int totalPages = sortedWorkspaces.isEmpty()
                ? 0
                : (int) Math.ceil((double) sortedWorkspaces.size() / size);

        return new PagedResult<>(
                items,
                page,
                size,
                sortedWorkspaces.size(),
                totalPages
        );
    }

    @Override
    public PagedResult<Workspace> findAllByOwnerId(String ownerId, int page, int size) {
        return null;
    }
}
package com.collab.workspace_service.application.service;

import com.collab.workspace_service.application.model.PagedResult;
import com.collab.workspace_service.application.port.in.ListWorkspacesQuery;
import com.collab.workspace_service.application.port.in.ListWorkspacesUseCase;
import com.collab.workspace_service.application.port.out.WorkspaceRepositoryPort;
import com.collab.workspace_service.domain.model.Workspace;

import java.util.Objects;

public class ListWorkspacesService implements ListWorkspacesUseCase {

    private static final int MAX_PAGE_SIZE = 100;

    private final WorkspaceRepositoryPort workspaceRepositoryPort;

    public ListWorkspacesService(WorkspaceRepositoryPort workspaceRepositoryPort) {
        this.workspaceRepositoryPort = Objects.requireNonNull(
                workspaceRepositoryPort,
                "Workspace repository port must not be null"
        );
    }

    @Override
    public PagedResult<Workspace> listWorkspaces(ListWorkspacesQuery query) {
        Objects.requireNonNull(query, "List workspaces query must not be null");

        validatePagination(query.page(), query.size());

        return workspaceRepositoryPort.findAll(
                query.page(),
                query.size()
        );
    }

    private void validatePagination(int page, int size) {
        if (page < 0) {
            throw new IllegalArgumentException("Page index must be greater than or equal to 0");
        }

        if (size <= 0) {
            throw new IllegalArgumentException("Page size must be greater than 0");
        }

        if (size > MAX_PAGE_SIZE) {
            throw new IllegalArgumentException("Page size must be less than or equal to " + MAX_PAGE_SIZE);
        }
    }
}
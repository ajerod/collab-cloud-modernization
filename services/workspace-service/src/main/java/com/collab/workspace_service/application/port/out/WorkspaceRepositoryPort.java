package com.collab.workspace_service.application.port.out;

import com.collab.workspace_service.application.model.PagedResult;
import com.collab.workspace_service.domain.model.Workspace;
import com.collab.workspace_service.domain.model.WorkspaceId;

import java.util.Optional;

public interface WorkspaceRepositoryPort {

    Workspace save(Workspace workspace);

    Optional<Workspace> findById(WorkspaceId workspaceId);

    PagedResult<Workspace> findAll(int page, int size);
}
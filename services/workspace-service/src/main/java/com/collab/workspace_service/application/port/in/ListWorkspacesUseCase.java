package com.collab.workspace_service.application.port.in;

import com.collab.workspace_service.application.model.PagedResult;
import com.collab.workspace_service.domain.model.Workspace;

public interface ListWorkspacesUseCase {

    PagedResult<Workspace> listWorkspaces(ListWorkspacesQuery query);
}
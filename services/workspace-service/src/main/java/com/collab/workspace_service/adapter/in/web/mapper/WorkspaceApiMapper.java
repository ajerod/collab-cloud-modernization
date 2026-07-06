package com.collab.workspace_service.adapter.in.web.mapper;

import com.collab.workspace_service.adapter.in.web.request.CreateWorkspaceRequest;
import com.collab.workspace_service.adapter.in.web.response.WorkspaceListResponse;
import com.collab.workspace_service.adapter.in.web.response.WorkspaceResponse;
import com.collab.workspace_service.application.model.PagedResult;
import com.collab.workspace_service.application.port.in.CreateWorkspaceCommand;
import com.collab.workspace_service.application.port.in.GetWorkspaceQuery;
import com.collab.workspace_service.application.port.in.ListWorkspacesQuery;
import com.collab.workspace_service.domain.model.Workspace;
import org.springframework.stereotype.Component;

@Component
public class WorkspaceApiMapper {

    public CreateWorkspaceCommand toCreateCommand(CreateWorkspaceRequest request) {
        return new CreateWorkspaceCommand(
                request.name(),
                request.ownerId()
        );
    }

    public GetWorkspaceQuery toGetQuery(String workspaceId) {
        return new GetWorkspaceQuery(workspaceId);
    }

    public ListWorkspacesQuery toListQuery(int page, int size) {
        return new ListWorkspacesQuery(page, size);
    }

    public WorkspaceResponse toResponse(Workspace workspace) {
        return new WorkspaceResponse(
                workspace.id().toString(),
                workspace.name(),
                workspace.ownerId(),
                workspace.createdAt()
        );
    }

    public WorkspaceListResponse toListResponse(PagedResult<Workspace> pagedResult) {
        return new WorkspaceListResponse(
                pagedResult.items()
                        .stream()
                        .map(this::toResponse)
                        .toList(),
                pagedResult.page(),
                pagedResult.size(),
                pagedResult.totalElements(),
                pagedResult.totalPages()
        );
    }
}
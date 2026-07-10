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

import java.util.Objects;

@Component
public class WorkspaceApiMapper {

    public CreateWorkspaceCommand toCommand(
            CreateWorkspaceRequest request,
            String authenticatedUserId
    ) {
        Objects.requireNonNull(request, "Create workspace request must not be null");

        return new CreateWorkspaceCommand(
                request.name(),
                authenticatedUserId
        );
    }

    public GetWorkspaceQuery toGetQuery(
            String workspaceId,
            String authenticatedUserId
    ) {
        return new GetWorkspaceQuery(
                workspaceId,
                authenticatedUserId
        );
    }

    public ListWorkspacesQuery toListQuery(
            String authenticatedUserId,
            int page,
            int size
    ) {
        return new ListWorkspacesQuery(
                authenticatedUserId,
                page,
                size
        );
    }

    public WorkspaceResponse toResponse(Workspace workspace) {
        Objects.requireNonNull(workspace, "Workspace must not be null");

        return new WorkspaceResponse(
                workspace.id().toString(),
                workspace.name(),
                workspace.ownerId(),
                workspace.createdAt()
        );
    }

    public WorkspaceListResponse toListResponse(PagedResult<Workspace> pagedResult) {
        Objects.requireNonNull(pagedResult, "Paged result must not be null");

        return new WorkspaceListResponse(
                pagedResult.items().stream()
                        .map(this::toResponse)
                        .toList(),
                pagedResult.page(),
                pagedResult.size(),
                pagedResult.totalElements(),
                pagedResult.totalPages()
        );
    }
}
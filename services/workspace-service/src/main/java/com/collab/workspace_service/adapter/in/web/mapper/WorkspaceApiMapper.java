package com.collab.workspace_service.adapter.in.web.mapper;

import com.collab.workspace_service.adapter.in.web.request.CreateWorkspaceRequest;
import com.collab.workspace_service.adapter.in.web.response.WorkspaceListResponse;
import com.collab.workspace_service.adapter.in.web.response.WorkspaceResponse;
import com.collab.workspace_service.application.model.PagedResult;
import com.collab.workspace_service.application.port.in.CreateWorkspaceCommand;
import com.collab.workspace_service.application.port.in.GetWorkspaceQuery;
import com.collab.workspace_service.application.port.in.ListWorkspacesQuery;
import com.collab.workspace_service.domain.model.Workspace;
import com.collab.workspace_service.domain.model.WorkspaceId;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface WorkspaceApiMapper {

    CreateWorkspaceCommand toCreateCommand(CreateWorkspaceRequest request);

    WorkspaceResponse toResponse(Workspace workspace);

    WorkspaceListResponse toListResponse(PagedResult<Workspace> pagedResult);

    default GetWorkspaceQuery toGetQuery(String workspaceId) {
        return new GetWorkspaceQuery(workspaceId);
    }

    default ListWorkspacesQuery toListQuery(int page, int size) {
        return new ListWorkspacesQuery(page, size);
    }

    default String map(WorkspaceId workspaceId) {
        if (workspaceId == null) {
            return null;
        }

        return workspaceId.toString();
    }
}
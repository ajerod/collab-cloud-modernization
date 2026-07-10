package com.collab.workspace_service.adapter.in.web.facade;

import com.collab.workspace_service.adapter.in.web.mapper.WorkspaceApiMapper;
import com.collab.workspace_service.adapter.in.web.request.CreateWorkspaceRequest;
import com.collab.workspace_service.adapter.in.web.response.WorkspaceListResponse;
import com.collab.workspace_service.adapter.in.web.response.WorkspaceResponse;
import com.collab.workspace_service.application.model.PagedResult;
import com.collab.workspace_service.application.port.in.CreateWorkspaceCommand;
import com.collab.workspace_service.application.port.in.CreateWorkspaceUseCase;
import com.collab.workspace_service.application.port.in.GetWorkspaceQuery;
import com.collab.workspace_service.application.port.in.GetWorkspaceUseCase;
import com.collab.workspace_service.application.port.in.ListWorkspacesQuery;
import com.collab.workspace_service.application.port.in.ListWorkspacesUseCase;
import com.collab.workspace_service.domain.model.Workspace;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class WorkspaceFacade {

    private final CreateWorkspaceUseCase createWorkspaceUseCase;
    private final GetWorkspaceUseCase getWorkspaceUseCase;
    private final ListWorkspacesUseCase listWorkspacesUseCase;
    private final WorkspaceApiMapper workspaceApiMapper;

    public WorkspaceFacade(
            CreateWorkspaceUseCase createWorkspaceUseCase,
            GetWorkspaceUseCase getWorkspaceUseCase,
            ListWorkspacesUseCase listWorkspacesUseCase,
            WorkspaceApiMapper workspaceApiMapper
    ) {
        this.createWorkspaceUseCase = Objects.requireNonNull(
                createWorkspaceUseCase,
                "Create workspace use case must not be null"
        );
        this.getWorkspaceUseCase = Objects.requireNonNull(
                getWorkspaceUseCase,
                "Get workspace use case must not be null"
        );
        this.listWorkspacesUseCase = Objects.requireNonNull(
                listWorkspacesUseCase,
                "List workspaces use case must not be null"
        );
        this.workspaceApiMapper = Objects.requireNonNull(
                workspaceApiMapper,
                "Workspace API mapper must not be null"
        );
    }

    public WorkspaceResponse createWorkspace(
            CreateWorkspaceRequest request,
            String authenticatedUserId
    ) {
        CreateWorkspaceCommand command = workspaceApiMapper.toCommand(
                request,
                authenticatedUserId
        );

        Workspace workspace = createWorkspaceUseCase.createWorkspace(command);

        return workspaceApiMapper.toResponse(workspace);
    }

    public WorkspaceResponse getWorkspace(
            String workspaceId,
            String authenticatedUserId
    ) {
        GetWorkspaceQuery query = workspaceApiMapper.toGetQuery(
                workspaceId,
                authenticatedUserId
        );

        Workspace workspace = getWorkspaceUseCase.getWorkspace(query);

        return workspaceApiMapper.toResponse(workspace);
    }

    public WorkspaceListResponse listWorkspaces(
            String authenticatedUserId,
            int page,
            int size
    ) {
        ListWorkspacesQuery query = workspaceApiMapper.toListQuery(
                authenticatedUserId,
                page,
                size
        );

        PagedResult<Workspace> result = listWorkspacesUseCase.listWorkspaces(query);

        return workspaceApiMapper.toListResponse(result);
    }
}
package com.collab.workspace_service.adapter.in.web.facade;

import com.collab.workspace_service.adapter.in.web.mapper.WorkspaceApiMapper;
import com.collab.workspace_service.adapter.in.web.request.CreateWorkspaceRequest;
import com.collab.workspace_service.adapter.in.web.response.WorkspaceResponse;
import com.collab.workspace_service.application.port.in.CreateWorkspaceUseCase;
import com.collab.workspace_service.application.port.in.GetWorkspaceUseCase;
import com.collab.workspace_service.domain.model.Workspace;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class WorkspaceFacade {

    private final CreateWorkspaceUseCase createWorkspaceUseCase;
    private final GetWorkspaceUseCase getWorkspaceUseCase;
    private final WorkspaceApiMapper workspaceApiMapper;

    public WorkspaceFacade(
            CreateWorkspaceUseCase createWorkspaceUseCase,
            GetWorkspaceUseCase getWorkspaceUseCase,
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
        this.workspaceApiMapper = Objects.requireNonNull(
                workspaceApiMapper,
                "Workspace API mapper must not be null"
        );
    }

    public WorkspaceResponse createWorkspace(CreateWorkspaceRequest request) {
        Workspace workspace = createWorkspaceUseCase.createWorkspace(
                workspaceApiMapper.toCreateCommand(request)
        );

        return workspaceApiMapper.toResponse(workspace);
    }

    public WorkspaceResponse getWorkspace(String workspaceId) {
        Workspace workspace = getWorkspaceUseCase.getWorkspace(
                workspaceApiMapper.toGetQuery(workspaceId)
        );

        return workspaceApiMapper.toResponse(workspace);
    }
}
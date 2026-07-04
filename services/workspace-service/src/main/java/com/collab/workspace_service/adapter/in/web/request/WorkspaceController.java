package com.collab.workspace_service.adapter.in.web;

import com.collab.workspace_service.adapter.in.web.request.CreateWorkspaceRequest;
import com.collab.workspace_service.adapter.in.web.response.WorkspaceResponse;
import com.collab.workspace_service.application.port.in.CreateWorkspaceCommand;
import com.collab.workspace_service.application.port.in.CreateWorkspaceUseCase;
import com.collab.workspace_service.domain.model.Workspace;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/workspaces")
public class WorkspaceController {

    private final CreateWorkspaceUseCase createWorkspaceUseCase;

    public WorkspaceController(CreateWorkspaceUseCase createWorkspaceUseCase) {
        this.createWorkspaceUseCase = createWorkspaceUseCase;
    }

    @PostMapping
    public ResponseEntity<WorkspaceResponse> createWorkspace(
            @Valid @RequestBody CreateWorkspaceRequest request
    ) {
        Workspace workspace = createWorkspaceUseCase.createWorkspace(
                new CreateWorkspaceCommand(
                        request.name(),
                        request.ownerId()
                )
        );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(WorkspaceResponse.from(workspace));
    }
}
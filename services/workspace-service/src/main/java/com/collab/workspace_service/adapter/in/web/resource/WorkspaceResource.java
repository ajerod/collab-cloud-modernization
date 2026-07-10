package com.collab.workspace_service.adapter.in.web.resource;


import com.collab.workspace_service.adapter.in.web.request.CreateWorkspaceRequest;
import com.collab.workspace_service.adapter.in.web.response.WorkspaceResponse;
import com.collab.workspace_service.application.port.in.CreateWorkspaceUseCase;
import com.collab.workspace_service.application.port.in.CreateWorkspaceCommand;
import com.collab.workspace_service.domain.model.Workspace;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/workspaces")
public class WorkspaceResource {

    private final CreateWorkspaceUseCase createWorkspaceUseCase;

    public WorkspaceResource(CreateWorkspaceUseCase createWorkspaceUseCase) {
        this.createWorkspaceUseCase = createWorkspaceUseCase;
    }

    @PostMapping
    public ResponseEntity<WorkspaceResponse> createWorkspace(
            @Valid @RequestBody CreateWorkspaceRequest request,
            @AuthenticationPrincipal Jwt jwt
    ) {
        String ownerId = jwt.getSubject();

        CreateWorkspaceCommand command = new CreateWorkspaceCommand(
                request.name(),
                ownerId
        );

        Workspace workspace = createWorkspaceUseCase.createWorkspace(command);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(WorkspaceResponse.from(workspace));
    }
}

package com.collab.workspace_service.adapter.in.web.resource;


import com.collab.workspace_service.adapter.in.web.facade.WorkspaceFacade;
import com.collab.workspace_service.adapter.in.web.request.CreateWorkspaceRequest;
import com.collab.workspace_service.adapter.in.web.resource.doc.WorkspaceApiDoc;
import com.collab.workspace_service.adapter.in.web.response.WorkspaceListResponse;
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
public class WorkspaceResource implements WorkspaceApiDoc {

    private final WorkspaceFacade workspaceFacade;

    public WorkspaceResource(WorkspaceFacade workspaceFacade) {
        this.workspaceFacade = workspaceFacade;
    }

    @Override
    @PostMapping
    public ResponseEntity<WorkspaceResponse> createWorkspace(
            @Valid @RequestBody CreateWorkspaceRequest request
    ) {
        WorkspaceResponse response = workspaceFacade.createWorkspace(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @Override
    @GetMapping("/{workspaceId}")
    public ResponseEntity<WorkspaceResponse> getWorkspace(
            @PathVariable String workspaceId
    ) {
        WorkspaceResponse response = workspaceFacade.getWorkspace(workspaceId);

        return ResponseEntity.ok(response);
    }

    @Override
    @GetMapping
    public ResponseEntity<WorkspaceListResponse> listWorkspaces(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        WorkspaceListResponse response = workspaceFacade.listWorkspaces(page, size);

        return ResponseEntity.ok(response);
    }
}

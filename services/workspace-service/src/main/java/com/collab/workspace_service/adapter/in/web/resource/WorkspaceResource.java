package com.collab.workspace_service.adapter.in.web.resource;

import com.collab.workspace_service.adapter.in.web.facade.WorkspaceFacade;
import com.collab.workspace_service.adapter.in.web.request.CreateWorkspaceRequest;
import com.collab.workspace_service.adapter.in.web.response.WorkspaceResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/workspaces")
public class WorkspaceResource {

    private final WorkspaceFacade workspaceFacade;

    public WorkspaceResource(WorkspaceFacade workspaceFacade) {
        this.workspaceFacade = workspaceFacade;
    }

    @PostMapping
    public ResponseEntity<WorkspaceResponse> createWorkspace(
            @Valid @RequestBody CreateWorkspaceRequest request
    ) {
        WorkspaceResponse response = workspaceFacade.createWorkspace(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("/{workspaceId}")
    public ResponseEntity<WorkspaceResponse> getWorkspace(
            @PathVariable String workspaceId
    ) {
        WorkspaceResponse response = workspaceFacade.getWorkspace(workspaceId);

        return ResponseEntity.ok(response);
    }
}
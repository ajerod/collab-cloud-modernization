package com.collab.workspace_service.adapter.in.web.resource;

import com.collab.workspace_service.adapter.in.web.facade.WorkspaceFacade;
import com.collab.workspace_service.adapter.in.web.request.CreateWorkspaceRequest;
import com.collab.workspace_service.adapter.in.web.resource.doc.WorkspaceApiDoc;
import com.collab.workspace_service.adapter.in.web.response.WorkspaceListResponse;
import com.collab.workspace_service.adapter.in.web.response.WorkspaceResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/api/workspaces")
@Validated
public class WorkspaceResource implements WorkspaceApiDoc {

    private final WorkspaceFacade workspaceFacade;

    public WorkspaceResource(WorkspaceFacade workspaceFacade) {
        this.workspaceFacade = Objects.requireNonNull(
                workspaceFacade,
                "Workspace facade must not be null"
        );
    }

    @Override
    @PostMapping
    public ResponseEntity<WorkspaceResponse> createWorkspace(
            @Valid @RequestBody CreateWorkspaceRequest request,
            @AuthenticationPrincipal Jwt jwt
    ) {
        WorkspaceResponse response = workspaceFacade.createWorkspace(
                request,
                jwt.getSubject()
        );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @Override
    @GetMapping("/{workspaceId}")
    public ResponseEntity<WorkspaceResponse> getWorkspace(
            @PathVariable String workspaceId,
            @AuthenticationPrincipal Jwt jwt
    ) {
        WorkspaceResponse response = workspaceFacade.getWorkspace(
                workspaceId,
                jwt.getSubject()
        );

        return ResponseEntity.ok(response);
    }

    @Override
    @GetMapping
    public ResponseEntity<WorkspaceListResponse> listWorkspaces(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @AuthenticationPrincipal Jwt jwt
    ) {
        WorkspaceListResponse response = workspaceFacade.listWorkspaces(
                jwt.getSubject(),
                page,
                size
        );

        return ResponseEntity.ok(response);
    }
}
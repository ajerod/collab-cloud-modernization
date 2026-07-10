package com.collab.workspace_service.adapter.in.web.resource.doc;

import com.collab.workspace_service.adapter.in.web.request.CreateWorkspaceRequest;
import com.collab.workspace_service.adapter.in.web.response.WorkspaceListResponse;
import com.collab.workspace_service.adapter.in.web.response.WorkspaceResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.Jwt;

@Tag(
        name = "Workspaces",
        description = "Workspace management API"
)
public interface WorkspaceApiDoc {

    @Operation(
            summary = "Create a workspace",
            description = "Creates a new workspace for the authenticated user."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Workspace created successfully",
                    content = @Content(schema = @Schema(implementation = WorkspaceResponse.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid request payload",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Missing or invalid access token",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))
            )
    })
    ResponseEntity<WorkspaceResponse> createWorkspace(
            @RequestBody(
                    required = true,
                    description = "Workspace creation payload. The owner is resolved from the authenticated JWT.",
                    content = @Content(schema = @Schema(implementation = CreateWorkspaceRequest.class))
            )
            @Valid CreateWorkspaceRequest request,

            @Parameter(hidden = true)
            Jwt jwt
    );

    @Operation(
            summary = "Get a workspace by id",
            description = "Retrieves an existing workspace using its unique identifier. The workspace must belong to the authenticated user."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Workspace found",
                    content = @Content(schema = @Schema(implementation = WorkspaceResponse.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid workspace id",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Missing or invalid access token",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Authenticated user is not allowed to access this workspace",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Workspace not found",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))
            )
    })
    ResponseEntity<WorkspaceResponse> getWorkspace(
            @Parameter(
                    description = "Workspace unique identifier",
                    example = "00000000-0000-0000-0000-000000000000"
            )
            String workspaceId,

            @Parameter(hidden = true)
            Jwt jwt
    );

    @Operation(
            summary = "List workspaces",
            description = "Lists workspaces owned by the authenticated user with simple pagination."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Workspaces listed successfully",
                    content = @Content(schema = @Schema(implementation = WorkspaceListResponse.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid pagination parameters",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Missing or invalid access token",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))
            )
    })
    ResponseEntity<WorkspaceListResponse> listWorkspaces(
            @Parameter(
                    description = "Page index, starting at 0",
                    example = "0"
            )
            @Min(0)
            int page,

            @Parameter(
                    description = "Page size",
                    example = "20"
            )
            @Min(1)
            @Max(100)
            int size,

            @Parameter(hidden = true)
            Jwt jwt
    );
}
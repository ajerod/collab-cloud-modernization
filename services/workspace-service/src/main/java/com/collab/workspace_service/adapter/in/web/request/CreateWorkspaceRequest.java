package com.collab.workspace_service.adapter.in.web.request;

import jakarta.validation.constraints.NotBlank;

public record CreateWorkspaceRequest(

        @NotBlank(message = "Workspace name must not be blank")
        String name,

        @NotBlank(message = "Owner id must not be blank")
        String ownerId
) {
}
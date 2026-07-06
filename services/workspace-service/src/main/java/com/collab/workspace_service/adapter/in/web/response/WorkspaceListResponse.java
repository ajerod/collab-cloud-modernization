package com.collab.workspace_service.adapter.in.web.response;

import java.util.List;

public record WorkspaceListResponse(
        List<WorkspaceResponse> items,
        int page,
        int size,
        long totalElements,
        int totalPages
) {
}
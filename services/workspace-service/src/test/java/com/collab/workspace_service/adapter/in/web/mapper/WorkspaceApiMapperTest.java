package com.collab.workspace_service.adapter.in.web.mapper;

import com.collab.workspace_service.adapter.in.web.request.CreateWorkspaceRequest;
import com.collab.workspace_service.adapter.in.web.response.WorkspaceListResponse;
import com.collab.workspace_service.adapter.in.web.response.WorkspaceResponse;
import com.collab.workspace_service.application.model.PagedResult;
import com.collab.workspace_service.application.port.in.CreateWorkspaceCommand;
import com.collab.workspace_service.application.port.in.GetWorkspaceQuery;
import com.collab.workspace_service.application.port.in.ListWorkspacesQuery;
import com.collab.workspace_service.domain.model.Workspace;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WorkspaceApiMapperTest {

    private final WorkspaceApiMapper mapper = Mappers.getMapper(WorkspaceApiMapper.class);

    @Test
    void shouldMapCreateWorkspaceRequestToCommand() {
        CreateWorkspaceRequest request = new CreateWorkspaceRequest(
                "Architecture Workspace"
        );

        CreateWorkspaceCommand command = mapper.toCreateCommand(request);

        assertEquals("Architecture Workspace", command.name());
        assertEquals("user-001", command.ownerId());
    }

    @Test
    void shouldMapWorkspaceToResponse() {
        Workspace workspace = Workspace.create(
                "Architecture Workspace",
                "user-001"
        );

        WorkspaceResponse response = mapper.toResponse(workspace);

        assertEquals(workspace.id().toString(), response.id());
        assertEquals("Architecture Workspace", response.name());
        assertEquals("user-001", response.ownerId());
        assertEquals(workspace.createdAt(), response.createdAt());
    }

    @Test
    void shouldMapPagedResultToWorkspaceListResponse() {
        Workspace workspace = Workspace.create(
                "Architecture Workspace",
                "user-001"
        );

        PagedResult<Workspace> pagedResult = new PagedResult<>(
                List.of(workspace),
                0,
                20,
                1,
                1
        );

        WorkspaceListResponse response = mapper.toListResponse(pagedResult);

        assertEquals(0, response.page());
        assertEquals(20, response.size());
        assertEquals(1, response.totalElements());
        assertEquals(1, response.totalPages());
        assertEquals(1, response.items().size());
        assertEquals(workspace.id().toString(), response.items().get(0).id());
    }

    @Test
    void shouldBuildGetWorkspaceQuery() {
        GetWorkspaceQuery query = mapper.toGetQuery(
                "00000000-0000-0000-0000-000000000000"
        );

        assertEquals(
                "00000000-0000-0000-0000-000000000000",
                query.workspaceId()
        );
    }

    @Test
    void shouldBuildListWorkspacesQuery() {
        ListWorkspacesQuery query = mapper.toListQuery(1, 10);

        assertEquals(1, query.page());
        assertEquals(10, query.size());
    }
}
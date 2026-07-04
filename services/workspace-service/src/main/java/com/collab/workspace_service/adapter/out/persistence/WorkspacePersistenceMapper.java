package com.collab.workspace_service.adapter.out.persistence;

import com.collab.workspace_service.domain.model.Workspace;
import com.collab.workspace_service.domain.model.WorkspaceId;

public final class WorkspacePersistenceMapper {

    private WorkspacePersistenceMapper() {
    }

    public static WorkspaceJpaEntity toEntity(Workspace workspace) {
        return new WorkspaceJpaEntity(
                workspace.id().value(),
                workspace.name(),
                workspace.ownerId(),
                workspace.createdAt()
        );
    }

    public static Workspace toDomain(WorkspaceJpaEntity entity) {
        return Workspace.rehydrate(
                new WorkspaceId(entity.getId()),
                entity.getName(),
                entity.getOwnerId(),
                entity.getCreatedAt()
        );
    }
}
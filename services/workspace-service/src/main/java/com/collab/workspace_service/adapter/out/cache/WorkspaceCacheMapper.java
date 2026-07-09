package com.collab.workspace_service.adapter.out.cache;

import com.collab.workspace_service.domain.model.Workspace;
import com.collab.workspace_service.domain.model.WorkspaceId;

public final class WorkspaceCacheMapper {

    private WorkspaceCacheMapper() {
    }

    public static WorkspaceCacheEntry toCacheEntry(Workspace workspace) {
        return new WorkspaceCacheEntry(
                workspace.id().toString(),
                workspace.name(),
                workspace.ownerId(),
                workspace.createdAt()
        );
    }

    public static Workspace toDomain(WorkspaceCacheEntry cacheEntry) {
        return Workspace.rehydrate(
                WorkspaceId.from(cacheEntry.id()),
                cacheEntry.name(),
                cacheEntry.ownerId(),
                cacheEntry.createdAt()
        );
    }
}
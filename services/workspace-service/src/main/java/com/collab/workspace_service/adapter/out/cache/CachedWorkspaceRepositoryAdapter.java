package com.collab.workspace_service.adapter.out.cache;

import com.collab.workspace_service.application.model.PagedResult;
import com.collab.workspace_service.application.port.out.WorkspaceRepositoryPort;
import com.collab.workspace_service.domain.model.Workspace;
import com.collab.workspace_service.domain.model.WorkspaceId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

import java.util.Objects;
import java.util.Optional;

public class CachedWorkspaceRepositoryAdapter implements WorkspaceRepositoryPort {

    private static final Logger LOGGER = LoggerFactory.getLogger(CachedWorkspaceRepositoryAdapter.class);

    private final WorkspaceRepositoryPort delegate;
    private final Cache workspaceCache;

    public CachedWorkspaceRepositoryAdapter(
            WorkspaceRepositoryPort delegate,
            CacheManager cacheManager
    ) {
        this.delegate = Objects.requireNonNull(
                delegate,
                "Workspace repository delegate must not be null"
        );

        Cache cache = Objects.requireNonNull(
                        cacheManager,
                        "Cache manager must not be null"
                )
                .getCache(WorkspaceCacheNames.WORKSPACES);

        this.workspaceCache = Objects.requireNonNull(
                cache,
                "Workspace cache must not be null"
        );
    }

    @Override
    public Workspace save(Workspace workspace) {
        Objects.requireNonNull(workspace, "Workspace must not be null");

        Workspace savedWorkspace = delegate.save(workspace);

        putInCache(savedWorkspace);

        return savedWorkspace;
    }

    @Override
    public Optional<Workspace> findById(WorkspaceId workspaceId) {
        Objects.requireNonNull(workspaceId, "Workspace id must not be null");

        Optional<Workspace> cachedWorkspace = findInCache(workspaceId);

        if (cachedWorkspace.isPresent()) {
            return cachedWorkspace;
        }

        Optional<Workspace> workspaceFromDatabase = delegate.findById(workspaceId);

        workspaceFromDatabase.ifPresent(this::putInCache);

        return workspaceFromDatabase;
    }

    @Override
    public PagedResult<Workspace> findAll(int page, int size) {
        LOGGER.info(
                "Retrieving workspace page without cache: page={}, size={}",
                page,
                size
        );

        return delegate.findAll(page, size);
    }

    @Override
    public PagedResult<Workspace> findAllByOwnerId(String ownerId, int page, int size) {
        return delegate.findAllByOwnerId(ownerId, page, size);
    }

    private Optional<Workspace> findInCache(WorkspaceId workspaceId) {
        String key = cacheKey(workspaceId);

        try {
            WorkspaceCacheEntry cacheEntry = workspaceCache.get(key, WorkspaceCacheEntry.class);

            if (cacheEntry == null) {
                LOGGER.info("Workspace cache MISS: workspaceId={}", workspaceId);
                return Optional.empty();
            }

            LOGGER.info("Workspace cache HIT: workspaceId={}", workspaceId);
            return Optional.of(WorkspaceCacheMapper.toDomain(cacheEntry));

        } catch (RuntimeException exception) {
            LOGGER.warn(
                    "Unable to read workspace from cache. Fallback to database: workspaceId={}",
                    workspaceId,
                    exception
            );
            return Optional.empty();
        }
    }

    private void putInCache(Workspace workspace) {
        String key = cacheKey(workspace.id());

        try {
            workspaceCache.put(
                    key,
                    WorkspaceCacheMapper.toCacheEntry(workspace)
            );

            LOGGER.info("Workspace stored in cache: workspaceId={}", workspace.id());

        } catch (RuntimeException exception) {
            LOGGER.warn(
                    "Unable to store workspace in cache: workspaceId={}",
                    workspace.id(),
                    exception
            );
        }
    }

    private String cacheKey(WorkspaceId workspaceId) {
        return workspaceId.value().toString();
    }
}
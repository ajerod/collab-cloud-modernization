package com.collab.workspace_service.adapter.out.persistence;

import com.collab.workspace_service.application.port.out.WorkspaceRepositoryPort;
import com.collab.workspace_service.domain.model.Workspace;
import com.collab.workspace_service.domain.model.WorkspaceId;

import java.util.Objects;
import java.util.Optional;

public class JpaWorkspaceRepositoryAdapter implements WorkspaceRepositoryPort {

    private final WorkspaceJpaRepository workspaceJpaRepository;

    public JpaWorkspaceRepositoryAdapter(WorkspaceJpaRepository workspaceJpaRepository) {
        this.workspaceJpaRepository = Objects.requireNonNull(
                workspaceJpaRepository,
                "Workspace JPA repository must not be null"
        );
    }

    @Override
    public Workspace save(Workspace workspace) {
        WorkspaceJpaEntity entity = WorkspacePersistenceMapper.toEntity(workspace);
        WorkspaceJpaEntity savedEntity = workspaceJpaRepository.save(entity);

        return WorkspacePersistenceMapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Workspace> findById(WorkspaceId workspaceId) {
        return workspaceJpaRepository
                .findById(workspaceId.value())
                .map(WorkspacePersistenceMapper::toDomain);
    }
}
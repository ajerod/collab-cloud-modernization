package com.collab.workspace_service.adapter.out.persistence;

import com.collab.workspace_service.application.model.PagedResult;
import com.collab.workspace_service.application.port.out.WorkspaceRepositoryPort;
import com.collab.workspace_service.domain.model.Workspace;
import com.collab.workspace_service.domain.model.WorkspaceId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

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

    @Override
    public PagedResult<Workspace> findAll(int page, int size) {
        PageRequest pageRequest = PageRequest.of(
                page,
                size,
                Sort.by(Sort.Direction.DESC, "createdAt")
        );

        Page<WorkspaceJpaEntity> result = workspaceJpaRepository.findAll(pageRequest);

        return new PagedResult<>(
                result.getContent()
                        .stream()
                        .map(WorkspacePersistenceMapper::toDomain)
                        .toList(),
                result.getNumber(),
                result.getSize(),
                result.getTotalElements(),
                result.getTotalPages()
        );
    }
}
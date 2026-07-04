package com.collab.workspace_service.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface WorkspaceJpaRepository extends JpaRepository<WorkspaceJpaEntity, UUID> {
}
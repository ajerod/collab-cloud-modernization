package com.collab.workspace_service.config;

import com.collab.workspace_service.adapter.out.cache.CachedWorkspaceRepositoryAdapter;
import com.collab.workspace_service.adapter.out.messaging.NoOpDomainEventPublisherAdapter;
import com.collab.workspace_service.adapter.out.persistence.JpaWorkspaceRepositoryAdapter;
import com.collab.workspace_service.adapter.out.persistence.WorkspaceJpaRepository;
import com.collab.workspace_service.application.port.in.CreateWorkspaceUseCase;
import com.collab.workspace_service.application.port.in.GetWorkspaceUseCase;
import com.collab.workspace_service.application.port.in.ListWorkspacesUseCase;
import com.collab.workspace_service.application.port.out.DomainEventPublisherPort;
import com.collab.workspace_service.application.port.out.WorkspaceRepositoryPort;
import com.collab.workspace_service.application.service.CreateWorkspaceService;
import com.collab.workspace_service.application.service.GetWorkspaceService;
import com.collab.workspace_service.application.service.ListWorkspacesService;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    public WorkspaceRepositoryPort workspaceRepositoryPort(
            WorkspaceJpaRepository workspaceJpaRepository,
            CacheManager cacheManager
    ) {
        WorkspaceRepositoryPort jpaWorkspaceRepositoryAdapter =
                new JpaWorkspaceRepositoryAdapter(workspaceJpaRepository);

        return new CachedWorkspaceRepositoryAdapter(
                jpaWorkspaceRepositoryAdapter,
                cacheManager
        );
    }

    @Bean
    public DomainEventPublisherPort domainEventPublisherPort() {
        return new NoOpDomainEventPublisherAdapter();
    }

    @Bean
    public CreateWorkspaceUseCase createWorkspaceUseCase(
            WorkspaceRepositoryPort workspaceRepositoryPort,
            DomainEventPublisherPort domainEventPublisherPort
    ) {
        return new CreateWorkspaceService(
                workspaceRepositoryPort,
                domainEventPublisherPort
        );
    }

    @Bean
    public GetWorkspaceUseCase getWorkspaceUseCase(
            WorkspaceRepositoryPort workspaceRepositoryPort
    ) {
        return new GetWorkspaceService(workspaceRepositoryPort);
    }

    @Bean
    public ListWorkspacesUseCase listWorkspacesUseCase(
            WorkspaceRepositoryPort workspaceRepositoryPort
    ) {
        return new ListWorkspacesService(workspaceRepositoryPort);
    }
}
package com.collab.workspace_service.config;

import com.collab.workspace_service.adapter.out.messaging.NoOpDomainEventPublisherAdapter;
import com.collab.workspace_service.adapter.out.persistence.JpaWorkspaceRepositoryAdapter;
import com.collab.workspace_service.adapter.out.persistence.WorkspaceJpaRepository;
import com.collab.workspace_service.application.port.in.CreateWorkspaceUseCase;
import com.collab.workspace_service.application.port.out.DomainEventPublisherPort;
import com.collab.workspace_service.application.port.out.WorkspaceRepositoryPort;
import com.collab.workspace_service.application.service.CreateWorkspaceService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    public WorkspaceRepositoryPort workspaceRepositoryPort(
            WorkspaceJpaRepository workspaceJpaRepository
    ) {
        return new JpaWorkspaceRepositoryAdapter(workspaceJpaRepository);
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
}
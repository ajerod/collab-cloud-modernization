package com.collab.workspace_service.application.port.out;

import com.collab.workspace_service.domain.event.DomainEvent;

public interface DomainEventPublisherPort {

    void publish(DomainEvent event);
}
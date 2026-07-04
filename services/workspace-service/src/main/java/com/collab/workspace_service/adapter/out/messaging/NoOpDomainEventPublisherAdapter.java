package com.collab.workspace_service.adapter.out.messaging;

import com.collab.workspace_service.application.port.out.DomainEventPublisherPort;
import com.collab.workspace_service.domain.event.DomainEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NoOpDomainEventPublisherAdapter implements DomainEventPublisherPort {

    private static final Logger LOGGER = LoggerFactory.getLogger(NoOpDomainEventPublisherAdapter.class);

    @Override
    public void publish(DomainEvent event) {
        LOGGER.info(
                "Domain event published: eventType={}, eventId={}, aggregateId={}",
                event.eventType(),
                event.eventId(),
                event.aggregateId()
        );
    }
}
package com.collab.workspace_service.domain.event;

import java.time.Instant;

public interface DomainEvent {

    String eventId();

    String eventType();

    String aggregateId();

    Instant occurredAt();
}
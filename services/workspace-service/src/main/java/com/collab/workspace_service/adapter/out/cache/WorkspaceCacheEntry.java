package com.collab.workspace_service.adapter.out.cache;

import java.io.Serializable;
import java.time.Instant;

public record WorkspaceCacheEntry(
        String id,
        String name,
        String ownerId,
        Instant createdAt
) implements Serializable {

    private static final long serialVersionUID = 1L;
}
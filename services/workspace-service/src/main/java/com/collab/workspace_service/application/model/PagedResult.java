package com.collab.workspace_service.application.model;

import java.util.List;
import java.util.Objects;

public record PagedResult<T>(
        List<T> items,
        int page,
        int size,
        long totalElements,
        int totalPages
) {

    public PagedResult {
        items = List.copyOf(Objects.requireNonNull(items, "Items must not be null"));
    }
}
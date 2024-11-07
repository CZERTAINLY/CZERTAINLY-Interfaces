package com.czertainly.api.model.core.logging.records;

import com.czertainly.api.model.core.auth.Resource;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Builder
public record ResourceRecord(
        @NotNull Resource type,
        List<UUID> uuids,
        List<String> names
) implements Serializable {
    public ResourceRecord(Resource type, UUID uuid, String name) {
        this(type, uuid == null ? null : List.of(uuid), name == null ? null : List.of(name));
    }
}
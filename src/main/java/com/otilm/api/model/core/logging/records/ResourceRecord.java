package com.otilm.api.model.core.logging.records;

import com.otilm.api.model.core.auth.Resource;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;
import lombok.Builder;

@Builder
public record ResourceRecord(@NotNull Resource type, List<ResourceObjectIdentity> objects) implements Serializable {
    public ResourceRecord(Resource type, UUID uuid, String name) {
        this(type, List.of(new ResourceObjectIdentity(name, uuid)));
    }
}

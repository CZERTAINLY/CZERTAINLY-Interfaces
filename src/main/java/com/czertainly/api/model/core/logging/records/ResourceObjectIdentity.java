package com.czertainly.api.model.core.logging.records;

import java.util.UUID;

public record ResourceObjectIdentity(
        String name,
        UUID uuid
) {
}

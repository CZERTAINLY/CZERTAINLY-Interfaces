package com.czertainly.api.model.core.logging.records;

import java.util.UUID;

public record NameAndUuid(
        String name,
        UUID uuid
) {
}

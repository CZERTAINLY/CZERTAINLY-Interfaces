package com.otilm.api.model.client.signing.profile.workflow.timestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

/**
 * A Timestamping Signing Profile, provided by this platform.
 */
@JsonIgnoreProperties(value = "type", allowGetters = true)
@Schema(name = "InternalTimestampSourceRequestDto",
        description = "Timestamp source referencing a Timestamping Signing Profile on this platform by UUID, as "
                + "supplied on create and update")
public record InternalTimestampSourceRequestDto(@NotNull @Schema(
        description = "UUID of the Timestamping Signing Profile that issues the timestamps. ILM "
                + "rejects a UUID that is not a Timestamping profile.",
        requiredMode = Schema.RequiredMode.REQUIRED) UUID signingProfileUuid) implements TimestampSourceRequestDto {

    @Override
    public TimestampSourceType getType() {
        return TimestampSourceType.INTERNAL;
    }
}

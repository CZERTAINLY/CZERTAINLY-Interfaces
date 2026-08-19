package com.otilm.api.model.client.signing.profile.workflow.timestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.otilm.api.model.common.NameAndUuidDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

/**
 * A Timestamping Signing Profile, provided by this platform.
 */
@JsonIgnoreProperties(value = "type", allowGetters = true)
@Schema(name = "InternalTimestampSourceDto",
        description = "Timestamp source referencing the Timestamping Signing Profile on this platform that issues the "
                + "timestamps")
public record InternalTimestampSourceDto(
        @NotNull @Schema(description = "Timestamping Signing Profile that issues the timestamps",
                requiredMode = Schema.RequiredMode.REQUIRED) NameAndUuidDto signingProfile)
        implements
            TimestampSourceDto {

    @Override
    public TimestampSourceType getType() {
        return TimestampSourceType.INTERNAL;
    }
}

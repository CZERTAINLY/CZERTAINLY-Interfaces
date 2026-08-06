package com.otilm.api.model.connector.discovery.v2;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Body for the discovery v2 /stream call: opens an event stream for items and progress
 * produced after the given sequence cursor.
 */
@Getter
@Setter
@ToString(callSuper = true)
public class DiscoveryStreamRequestDto extends DiscoveryV2ScopedRequestDto {

    @Schema(description = "Cursor: stream items with sequence greater than this value. "
                  + "Item sequences are dense per run, so 0 requests items from the start of the run.",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED,
            minimum = "0",
            defaultValue = "0")
    @PositiveOrZero
    private long afterSequence;
}

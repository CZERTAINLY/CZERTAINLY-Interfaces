package com.otilm.api.model.connector.discovery.v2;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Body for the discovery v2 /results call: pulls a batch of discovered items produced
 * after the given sequence cursor.
 */
@Getter
@Setter
@ToString(callSuper = true)
public class DiscoveryDrainRequestDto extends DiscoveryV2ScopedRequestDto {

    @Schema(description = "Cursor: return items with sequence greater than this value. "
                  + "Item sequences are dense per run, so 0 requests items from the start of the run.",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED,
            minimum = "0",
            defaultValue = "0")
    @PositiveOrZero
    private long afterSequence;

    @Schema(description = "Maximum number of items to return in this batch. The platform's tunneled "
                  + "transport caps a single response at 10 MiB; a connector MUST apply its own bound on "
                  + "the returned page when this is omitted.",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED,
            minimum = "1")
    @Positive
    private Integer maxItems;

    @Schema(description = "Maximum serialized size, in bytes, of the items returned in this batch. The "
                  + "platform's tunneled transport caps a single response at 10 MiB (10485760 bytes); "
                  + "exceeding it is an unrecoverable run failure. A connector MUST apply its own bound "
                  + "when this is omitted.",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED,
            minimum = "1",
            maximum = "10485760")
    @Positive
    private Long maxBytes;
}

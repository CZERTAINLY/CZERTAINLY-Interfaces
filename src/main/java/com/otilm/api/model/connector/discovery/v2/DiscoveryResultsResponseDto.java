package com.otilm.api.model.connector.discovery.v2;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Body returned by the discovery v2 /results call: a page of discovered items.
 */
@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DiscoveryResultsResponseDto {

    @Schema(description = "Items discovered after the requested cursor, up to the batch limits. A producer "
            + "that means \"this page has no items\" MUST send an explicit empty array; omitting the "
            + "field is rejected rather than read as \"no discoveries\".", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "items is required (send an explicit empty array for a page with no items)")
    private List<@NotNull(message = "items must not contain a null item") @Valid DiscoveredItemDto> items;

    @Schema(description = "Run-wide highest item sequence assigned so far — never page-scoped. Consumers must "
            + "advance cursors only by item sequences actually received. Item sequences start at 1, so 0 "
            + "means the run has produced no items yet.", requiredMode = Schema.RequiredMode.REQUIRED, minimum = "0")
    @NotNull(message = "highestSequence is required")
    @PositiveOrZero(message = "highestSequence must not be negative")
    private Long highestSequence;

    @Schema(description = "True when the connector has additional items beyond this page",
            requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "more is required")
    private Boolean more;
}

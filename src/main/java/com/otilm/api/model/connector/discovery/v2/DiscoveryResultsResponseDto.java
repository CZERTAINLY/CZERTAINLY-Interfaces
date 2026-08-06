package com.otilm.api.model.connector.discovery.v2;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

/**
 * Body returned by the discovery v2 /results call: a page of discovered items.
 */
@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DiscoveryResultsResponseDto {

    @Schema(description = "Items discovered after the requested cursor, up to the batch limits",
            requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "items is required")
    @Valid
    private List<DiscoveredItemDto> items = new ArrayList<>();

    @Schema(description = "Run-wide highest item sequence assigned so far — never page-scoped. Consumers must "
                  + "advance cursors only by item sequences actually received.",
            requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "highestSequence is required")
    private Long highestSequence;

    @Schema(description = "True when the connector has additional items beyond this page",
            requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "more is required")
    private Boolean more;
}

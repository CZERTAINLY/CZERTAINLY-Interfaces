package com.otilm.api.model.connector.discovery.v2;

import com.otilm.api.model.core.auth.Resource;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;

/**
 * Progress detail for a discovery run, reused in two roles: the {@code progress} field of
 * {@link DiscoveryStatusResponseDto} (polled), and — via the {@code type}-carrying subclass
 * {@link com.otilm.api.model.connector.discovery.v2.event.DiscoveryProgressEvent} — the flat
 * {@code progress} stream/AMQP event (pushed). Every field here is optional; a connector that
 * cannot estimate progress at all sends an all-null instance.
 */
@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class DiscoveryProgressDto {

    @Schema(description = "Number of items processed so far; omitted when the connector cannot report it",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Long processed;

    @Schema(description = "Estimated total number of items for the run; omitted when the connector cannot "
                  + "produce an estimate",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Long totalEstimate;

    @Schema(description = "Connector-defined free-text phase label (e.g. \"scanning\", \"enumerating\"); "
                  + "omitted when the connector has no phase concept",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String phase;

    @Schema(description = "Per-resource breakdown using this same progress shape, keyed by resource code "
                  + "(e.g. \"certificates\", \"keys\"); omitted when the connector reports only run-level progress",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED,
            propertyNames = Resource.class)
    private Map<Resource, DiscoveryProgressDto> byResource;
}

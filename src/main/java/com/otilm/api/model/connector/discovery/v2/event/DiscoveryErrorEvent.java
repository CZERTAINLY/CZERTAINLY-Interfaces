package com.otilm.api.model.connector.discovery.v2.event;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.otilm.api.model.connector.discovery.v2.DiscoveryEvent;
import com.otilm.api.model.connector.discovery.v2.DiscoveryEventType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Flat discovery {@code error} event. Advisory and non-terminal: Core folds this into the run's message/failure-reason
 * log. Terminal failure is only ever committed from a {@code status} response with state {@code FAILED}.
 */
@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class DiscoveryErrorEvent implements DiscoveryEvent {

    @Schema(description = "Event type; this event's own discriminator field",
            requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "type is required")
    private DiscoveryEventType type = DiscoveryEventType.ERROR;

    /**
     * Bounded because the platform uses it as the identity of a kind of problem and aggregates repeats onto it, so a
     * code outside this bound is not recorded as the connector sent it — it is replaced by the platform's own, rather
     * than shortened into an identity no connector chose. Published here as the limit to write to; a response body is
     * not bean-validated, so nothing rejects an oversized code on arrival.
     */
    @Schema(description = "Connector-defined error code", maxLength = 64, requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "code is required")
    @Size(max = 64, message = "code must not exceed 64 characters")
    private String code;

    @Schema(description = "Human-readable error message — curated message text (no raw exception messages)",
            requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "message is required")
    private String message;
}

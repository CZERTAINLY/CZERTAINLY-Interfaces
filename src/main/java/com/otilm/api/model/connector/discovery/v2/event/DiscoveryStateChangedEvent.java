package com.otilm.api.model.connector.discovery.v2.event;

import com.otilm.api.model.connector.discovery.v2.DiscoveryEvent;
import com.otilm.api.model.connector.discovery.v2.DiscoveryEventType;
import com.otilm.api.model.connector.discovery.v2.DiscoveryRunState;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Flat discovery {@code stateChanged} event. Advisory only: Core verifies any transition via an
 * authoritative {@code status} call before committing it; this event never commits a transition
 * on its own.
 */
@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class DiscoveryStateChangedEvent implements DiscoveryEvent {

    @Schema(description = "Event type; this event's own discriminator field",
            requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "type is required")
    private DiscoveryEventType type = DiscoveryEventType.STATE_CHANGED;

    @Schema(description = "Run state the connector is reporting; advisory, not authoritative",
            requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "state is required")
    private DiscoveryRunState state;
}

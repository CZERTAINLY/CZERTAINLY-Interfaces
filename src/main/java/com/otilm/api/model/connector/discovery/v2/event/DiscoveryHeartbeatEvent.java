package com.otilm.api.model.connector.discovery.v2.event;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.otilm.api.model.connector.discovery.v2.DiscoveryEvent;
import com.otilm.api.model.connector.discovery.v2.DiscoveryEventType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import java.time.OffsetDateTime;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Flat discovery {@code heartbeat} event: a stream liveness signal with no data of its own beyond {@code sentAt}.
 * Poll-mode consumers never need it.
 */
@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class DiscoveryHeartbeatEvent implements DiscoveryEvent {

    @Schema(description = "Event type; this event's own discriminator field", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "type is required")
    private DiscoveryEventType type = DiscoveryEventType.HEARTBEAT;

    @Schema(description = "Timestamp at which the connector sent this heartbeat", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "sentAt is required")
    private OffsetDateTime sentAt;
}

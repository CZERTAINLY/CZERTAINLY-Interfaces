package com.otilm.api.model.connector.discovery.v2.event;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.otilm.api.model.connector.discovery.v2.DiscoveryEvent;
import com.otilm.api.model.connector.discovery.v2.DiscoveryEventType;
import com.otilm.api.model.connector.discovery.v2.DiscoveryProgressDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Flat discovery {@code progress} event. Extends {@link DiscoveryProgressDto} — rather than duplicating its fields on a
 * separate class — purely to add {@code type} at the top level. Do not add {@code type} to {@link DiscoveryProgressDto}
 * itself: only the event is discriminated, and a nested {@code byResource} entry must never carry one.
 */
@Getter
@Setter
@ToString(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class DiscoveryProgressEvent extends DiscoveryProgressDto implements DiscoveryEvent {

    @Schema(description = "Event type; this event's own discriminator field",
            requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "type is required")
    private DiscoveryEventType type = DiscoveryEventType.PROGRESS;
}

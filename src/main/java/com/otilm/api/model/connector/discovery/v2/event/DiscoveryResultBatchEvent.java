package com.otilm.api.model.connector.discovery.v2.event;

import com.otilm.api.model.connector.discovery.v2.DiscoveredItemDto;
import com.otilm.api.model.connector.discovery.v2.DiscoveryEvent;
import com.otilm.api.model.connector.discovery.v2.DiscoveryEventType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * Flat discovery {@code resultBatch} event: a batch of discovered items pushed on a held-open
 * NDJSON stream or an AMQP {@code discovery.event} message. Each item carries its own per-run
 * sequence; the event itself is unsequenced.
 */
@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class DiscoveryResultBatchEvent implements DiscoveryEvent {

    @Schema(description = "Event type; this event's own discriminator field",
            requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "type is required")
    private DiscoveryEventType type = DiscoveryEventType.RESULT_BATCH;

    @Schema(description = "Discovered items in this batch, each carrying its own per-run sequence. A producer "
                  + "that means \"this batch has no items\" MUST send an explicit empty array; omitting the "
                  + "field is rejected rather than read as \"no discoveries\".",
            requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "items is required (send an explicit empty array for a batch with no items)")
    private List<@NotNull(message = "items must not contain a null item") @Valid DiscoveredItemDto> items;
}

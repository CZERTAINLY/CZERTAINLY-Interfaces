package com.otilm.api.model.core.discovery;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.otilm.api.model.connector.discovery.v2.DiscoveredItemPayloadDto;
import com.otilm.api.model.core.auth.Resource;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.OffsetDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * One item a Discovery run staged, as Core holds it — the resource-agnostic counterpart to
 * {@link DiscoveryCertificateDto}.
 *
 * <p>
 * Core-facing on purpose, rather than returning the connector's own {@code DiscoveredItemDto}. What a connector
 * reported and what Core did with it are different facts, and only Core knows the second: whether the item was
 * processed into inventory and why it failed if it did. Those fields cannot live on the connector DTO, because a
 * connector has no business being told about Core's processing state. Keeping this type here also means later columns
 * arrive additively instead of altering a published connector contract.
 *
 * <p>
 * {@code payload} deliberately reuses the connector-defined union: the resource-specific data a connector reports is
 * genuinely shared, and re-declaring it Core-side would be duplication that could drift.
 *
 * <p>
 * {@code inventoryUuid} is published, so a staged item can be traced to the object it became — parity with the
 * certificate listing. It is nullable by nature rather than by omission: an item that has not been processed, or whose
 * processing failed, never produced an inventory object. Core stamps it during ingestion.
 *
 * <p>
 * {@code newlyDiscovered} carries the same meaning it has for certificates — the object was not already in inventory
 * when this run staged it, matched by fingerprint. It is not a display convenience: for certificates the equivalent
 * count decides whether a run enters its processing phase at all, and it is the axis the results view separates on.
 * Keys report a fingerprint and key ingestion already deduplicates against inventory, so the same fact is available and
 * carries the same weight for them. Core computes it during ingestion the way {@code CertificateHandler} does.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class DiscoveryItemDto {

    @Schema(description = "UUID of the staged Discovery item", requiredMode = Schema.RequiredMode.REQUIRED)
    private String uuid;

    @Schema(description = "UUID of the object this item became in inventory. Absent until the item is processed, and "
            + "absent permanently if its processing failed.")
    private String inventoryUuid;

    // Primitive, unlike the connector's DiscoveredItemDto.sequence: that one is inbound, where a boxed Long lets a
    // value the connector omitted arrive as null and be rejected rather than silently becoming 0. This one is
    // outbound and always populated — the connector's run-wide cursor for v2-staged items, synthesized by Core for
    // items a v1 provider staged — so a box would only invent an absent state the contract says cannot occur.
    @Schema(description = "Position of this item in the run. For runs against a v2 Discovery Provider this is the "
            + "provider's run-wide sequence; for runs against a v1 provider, which never numbered its items, Core "
            + "synthesizes it from staging order.", requiredMode = Schema.RequiredMode.REQUIRED)
    private long sequence;

    @Schema(description = "Reference identifying this item within the run, unique per resource type. "
            + "Provider-assigned for v2-staged items; for certificates staged by a v1 provider Core uses the "
            + "certificate fingerprint, which is the identity it deduplicates on.", requiredMode = Schema.RequiredMode.REQUIRED)
    private String uniqueRef;

    @Schema(description = "When the Discovery Provider reported discovering this item")
    private OffsetDateTime discoveredAt;

    @Schema(description = "Resource-specific data the Discovery Provider reported, discriminated by resource", requiredMode = Schema.RequiredMode.REQUIRED)
    private DiscoveredItemPayloadDto payload;

    @Schema(description = "True when the object was not already in the inventory at the time this run staged it, "
            + "false when the run rediscovered something the inventory already held.", requiredMode = Schema.RequiredMode.REQUIRED)
    private boolean newlyDiscovered;

    @Schema(description = "Indicator whether the staged item has already been processed into inventory.", requiredMode = Schema.RequiredMode.REQUIRED)
    private boolean processed;

    @Schema(description = "Error message in case of failed processing of the staged item.")
    private String processedError;

    /**
     * Derived from the payload rather than stored, so the two can never disagree — the same reasoning as the
     * connector's {@code DiscoveredItemDto}, which hides its derived accessor with {@code @JsonIgnore}. This one stays
     * on the wire deliberately: it is the value the {@code resource} query filter matches on, and clients group by it
     * without reaching into the payload union.
     */
    // READ_ONLY, not just getter-only: a bare getter serializes but is unknown to deserialization, so a strict
    // mapper (FAIL_ON_UNKNOWN_PROPERTIES) would reject this DTO's own serialized form. READ_ONLY registers the
    // property and ignores it on input, so the derived value always wins.
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(description = "Resource type of this item", requiredMode = Schema.RequiredMode.REQUIRED)
    public Resource getResource() {
        return payload != null ? payload.getResource() : null;
    }
}

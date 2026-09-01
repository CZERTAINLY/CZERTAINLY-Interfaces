package com.otilm.api.model.connector.discovery.v2;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.otilm.api.model.core.auth.Resource;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Run-level progress detail, reused in two roles: the {@code progress} field of {@link DiscoveryStatusResponseDto}
 * (polled), and — via the {@code type}-carrying subclass
 * {@link com.otilm.api.model.connector.discovery.v2.event.DiscoveryProgressEvent} — the flat {@code progress}
 * stream/AMQP event (pushed).
 *
 * <p>
 * The run-level counters are inherited from {@link DiscoveryResourceProgressDto}; this class adds only the optional
 * per-resource breakdown, whose values are the leaf type. That asymmetry is deliberate and load-bearing in two ways. It
 * states the truth that nesting stops after one level, and it keeps the generated schema graph finite: when
 * {@code byResource} referred to this same class, swagger-core emitted a truncated {@code DiscoveryProgressDto}
 * component — missing {@code byResource} entirely, and carrying the byResource field's description as the component's
 * own — whenever the graph was entered through {@link DiscoveryEvent}, which is exactly what resolving the stream
 * endpoint's response does.
 */
@Getter
@Setter
@ToString(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
// The description lives here, on the component itself, so it reads the same from every endpoint that references
// it — a description on any referencing field would be hoisted over it (see the field comments below and
// progressComponentsAreIdenticalFromEveryEntryPoint).
@Schema(description = "Run-level progress of a discovery run: the run-wide counters, plus an optional "
        + "per-resource breakdown.")
public class DiscoveryProgressDto extends DiscoveryResourceProgressDto {

    // A plain @Schema, unlike the $ref-valued fields elsewhere in this contract: a Map's $ref sits under
    // additionalProperties rather than beside the description, so there is nothing for swagger-core to hoist
    // onto the shared component. ALL_OF_REF would be wrong here -- on a Map field it drops the description.
    @Schema(description = "Per-resource progress, keyed by resource code. Present only when the connector "
            + "breaks its progress down by resource; the run-wide counters above stand alone otherwise.",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED, propertyNames = Resource.class)
    private Map<Resource, DiscoveryResourceProgressDto> byResource;
}

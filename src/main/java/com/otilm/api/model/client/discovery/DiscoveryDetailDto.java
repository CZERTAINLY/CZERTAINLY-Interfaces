package com.otilm.api.model.client.discovery;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.otilm.api.model.client.attribute.ResponseAttribute;
import com.otilm.api.model.client.metadata.MetadataResponseDto;
import com.otilm.api.model.common.NameAndUuidDto;
import com.otilm.api.model.connector.discovery.v2.DiscoveryProgressDto;
import com.otilm.api.model.connector.discovery.v2.DiscoveryResourceCapability;
import com.otilm.api.model.core.auth.Resource;
import com.otilm.api.model.core.discovery.DiscoveryStatus;
import com.otilm.api.model.core.workflows.TriggerDto;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class DiscoveryDetailDto extends NameAndUuidDto {

    @Schema(description = "Discovery Kind", examples = {"IP-HostName"}, requiredMode = Schema.RequiredMode.REQUIRED)
    private String kind;

    @Schema(description = "Status of Discovery", requiredMode = Schema.RequiredMode.REQUIRED)
    private DiscoveryStatus status;

    @Schema(description = "Status of Discovery returned by connector", requiredMode = Schema.RequiredMode.REQUIRED)
    private DiscoveryStatus connectorStatus;

    @Schema(description = "Failure/Success Messages", examples = {"Failed due to network connectivity issues"})
    private String message;

    @Schema(description = "Date and time when Discovery started", nullable = true)
    private Date startTime;

    @Schema(description = "Date and time when Discovery finished", nullable = true)
    private Date endTime;

    @Schema(description = "Number of certificates that are discovered", defaultValue = "0")
    private Integer totalCertificatesDiscovered;

    @Schema(description = "Number of certificates that were discovered by connector", defaultValue = "0")
    private Integer connectorTotalCertificatesDiscovered;

    @Schema(description = "UUID of the Discovery Provider", requiredMode = Schema.RequiredMode.REQUIRED)
    private String connectorUuid;

    @Schema(description = "Name of the Discovery Provider", requiredMode = Schema.RequiredMode.REQUIRED)
    private String connectorName;

    @Schema(description = "List of Discovery Attributes", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<ResponseAttribute> attributes = new ArrayList<>();

    @Schema(description = "List of Custom Attributes")
    private List<ResponseAttribute> customAttributes;

    @Schema(description = "Metadata of the Discovery")
    private List<MetadataResponseDto> metadata;

    @Schema(description = "List of associated triggers", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<TriggerDto> triggers = new ArrayList<>();

    // The four fields below are the discovery v2 additions. Each is individually annotated
    // @JsonInclude(NON_NULL) rather than the class carrying it, so a v1 run's payload keeps
    // emitting its existing nullable fields (message, startTime, endTime, ...) exactly as before —
    // a class-level annotation would have silently changed the shape of every v1 response.
    // resources and effectiveCapabilities are nonetheless always present: Core synthesizes both for a
    // v1-connector run, so their NON_NULL only keeps a broken mapper's null off the wire as a loud
    // absence rather than a quiet null.

    // The arraySchema indirection on the two typed lists is deliberate: a bare @Schema description on a
    // collection member lands on the items schema — a $ref to a shared component — and swagger-core hoists
    // it onto that component for every other API referencing it. arraySchema puts the text on the array.
    @ArraySchema(arraySchema = @Schema(description = "Resource types this run targets, as resource wire codes (e.g. "
            + "\"certificates\", \"keys\"). Always present: a run against a v1 connector reports "
            + "[\"certificates\"], synthesized by Core, so a client never inspects the connector "
            + "to learn what a run was after.", requiredMode = Schema.RequiredMode.REQUIRED))
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<Resource> resources;

    /**
     * Progress counters reported by the connector, with an optional per-resource breakdown. Omitted when the run is
     * against a v1 connector, or when the connector reports no progress at all. Individual counters inside it are
     * independently optional — a connector that cannot estimate a total still reports what it has processed.
     *
     * <p>
     * The prose lives here and not in {@code @Schema}: the field's schema is a {@code $ref} to
     * {@code DiscoveryProgressDto}, shared with the connector-facing status response, and OpenAPI 3.0 cannot carry a
     * description beside a {@code $ref} — swagger-core would hoist it onto the shared component
     * ({@code progressComponentsAreIdenticalFromEveryEntryPoint} pins this).
     */
    @Schema(requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private DiscoveryProgressDto progress;

    @Schema(description = "Advisory messages collected over the run's lifetime — non-fatal connector "
            + "errors and per-phase failure reasons — newest last. Distinct from message, which "
            + "carries the single summary reason for the run's current status: entries here do "
            + "not imply the run failed, and a run can accumulate them and still complete. "
            + "Curated message text; never a raw exception message.", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<String> runMessages;

    @ArraySchema(arraySchema = @Schema(
            description = "Capabilities effective for this run: the intersection of what the connector's "
                    + "discovery interface supports across every resource type the run targets, so a "
                    + "client can decide whether the stop and resume operations exist for this run "
                    + "without reasoning about connector feature flags itself. A capability any targeted "
                    + "resource lacks is not listed. Always present: empty means the run supports none "
                    + "of them — true for every run against a v1 connector, and for a v2 run whose "
                    + "targeted resources share no capability. Computed from the connector's "
                    + "per-resource capabilities, where a null list is the connector's shorthand for "
                    + "every capability it advertises — expanded before intersecting, never treated "
                    + "as an empty set.",
            requiredMode = Schema.RequiredMode.REQUIRED))
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<DiscoveryResourceCapability> effectiveCapabilities;
}

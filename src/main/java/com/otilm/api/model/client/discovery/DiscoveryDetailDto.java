package com.otilm.api.model.client.discovery;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.otilm.api.model.client.attribute.ResponseAttribute;
import com.otilm.api.model.client.metadata.MetadataResponseDto;
import com.otilm.api.model.common.NameAndUuidDto;
import com.otilm.api.model.connector.discovery.v2.DiscoveryProgressDto;
import com.otilm.api.model.core.auth.Resource;
import com.otilm.api.model.core.discovery.DiscoveryStatus;
import com.otilm.api.model.core.workflows.TriggerDto;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.OffsetDateTime;
import java.util.ArrayList;
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
    private OffsetDateTime startTime;

    @Schema(description = "Date and time when Discovery finished", nullable = true)
    private OffsetDateTime endTime;

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

    // The four fields below are the discovery v2 additions, each individually @JsonInclude(NON_NULL) so a
    // v1 run's payload keeps its existing shape — a class-level annotation would have silently changed
    // every v1 response. resources and stoppable are still always present (Core synthesizes them for
    // v1-connector runs); their NON_NULL only turns a broken mapper's null into a loud absence.

    // The arraySchema indirection is deliberate: a bare @Schema description on a collection member lands
    // on the items schema — a $ref to a shared component — and swagger-core hoists it onto that component
    // for every other API referencing it. arraySchema puts the text on the array.
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
     * The prose lives here and not in {@code @Schema} for the hoisting reason in the comment above
     * ({@code progressComponentsAreIdenticalFromEveryEntryPoint} pins it).
     */
    @Schema(requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private DiscoveryProgressDto progress;

    /**
     * A count rather than the log itself. The messages are their own paged resource
     * ({@code GET /v1/discoveries/{uuid}/messages}), because a client polls this detail while a run is live and a log
     * bounded only by "large" would ride along on every poll. The count is enough to badge the log without reading it,
     * and it is what tells a client whether the log is worth opening at all.
     *
     * <p>
     * <b>Relation to {@code message}:</b> {@code message} carries the single summary reason for the run's current
     * status; the messages this counts are advisory, accumulate over the run's lifetime, and a run can collect them and
     * still complete.
     *
     * <p>
     * Primitive and REQUIRED: a run with nothing to report has none, which is 0 rather than absent. Runs against a v1
     * Discovery Provider always report 0 — the log is a v2 construct.
     */
    @Schema(description = "How many distinct advisory messages this run collected, as counted by the run messages "
            + "listing. Repeated problems are aggregated, so this counts kinds of problem rather than occurrences. "
            + "A non-zero count does not imply the run failed. Always 0 for runs against a v1 Discovery Provider.",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private long runMessageCount;

    /**
     * <b>Provenance:</b> declared by the connector at initiate and refreshed on resume; derived by Core from the
     * interface-level {@code discoveryStopResume} flag when the connector left it undeclared.
     *
     * <p>
     * <b>Presence:</b> synthesized as {@code false} for a run against a v1 connector, which cannot stop.
     */
    @Schema(description = "Whether this run can be stopped and later resumed. Always present; false for "
            + "runs against v1 connectors. This flag says whether the run has the capability; the run "
            + "status decides which control is currently valid (stop while in progress, resume while "
            + "stopped). The connector may still refuse a stop at runtime past the point of no return.",
            requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Boolean stoppable;
}

package com.otilm.api.model.connector.discovery.v2;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.otilm.api.model.common.attribute.common.MetadataAttribute;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Body returned by the discovery v2 /initiate call.
 */
@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DiscoveryInitiateResponseDto {

    // Excluded from toString for the same reason the request side excludes it: meta is an opaque
    // connector-defined handle with no logging value of its own, and up to 64 KB of it. JSON
    // serialization is unaffected.
    @Schema(description = "Connector-defined metadata for this run, replayed by Core on every subsequent "
            + "lifecycle call — status, results, stream, stop, resume and cancel — so the stateless "
            + "connector can resolve its run state. Serialized size is capped at 64 KB; Core fails the "
            + "run outright if this exceeds the cap.", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @ToString.Exclude
    private List<MetadataAttribute> meta;

    @Schema(description = "Whether this run can be stopped and later resumed. Declared per run because "
            + "checkpointability may depend on the resources scanned and the scan parameters, not only on "
            + "the connector as a whole. Core snapshots the value onto the run and renders the stop and "
            + "resume controls from it; the connector may still refuse a stop at runtime past the point "
            + "of no return. Absent means undeclared — Core then gates on the discoveryStopResume feature "
            + "flag alone. A resume response refreshes the snapshot.", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Boolean stoppable;
}

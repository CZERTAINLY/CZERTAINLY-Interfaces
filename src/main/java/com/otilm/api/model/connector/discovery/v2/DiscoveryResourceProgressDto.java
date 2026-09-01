package com.otilm.api.model.connector.discovery.v2;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Progress counters for a single resource type. Every field is optional; a connector that cannot report progress at all
 * omits the whole object rather than sending an empty one, since the two say the same thing and a consumer keeping the
 * last known snapshot cannot tell an empty report from a missing one.
 *
 * <p>
 * This is the leaf of the progress model, and deliberately carries no {@code byResource} of its own: a per-resource
 * breakdown of a per-resource breakdown has no meaning. Keeping it a leaf also keeps the generated schema graph finite
 * — a self-referential progress type made swagger-core emit a truncated {@code DiscoveryProgressDto} component whenever
 * the graph was entered through {@link DiscoveryEvent}, silently dropping {@code byResource} from the published
 * contract that Go and Python connector authors generate their clients from.
 */
@Getter
@Setter
@ToString
@Schema(description = "Progress counters. Used for a whole run and, keyed by resource code inside "
        + "byResource (e.g. \"certificates\", \"keys\"), for one resource type at a time. Every "
        + "field is optional.")
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class DiscoveryResourceProgressDto {

    @Schema(description = "Number of items processed so far; omitted when the connector cannot report it",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Long processed;

    @Schema(description = "Estimated total number of items for the run; omitted when the connector cannot "
            + "produce an estimate", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Long totalEstimate;

    @Schema(description = "Connector-defined free-text phase label (e.g. \"scanning\", \"enumerating\"); "
            + "omitted when the connector has no phase concept", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String phase;

    @Schema(description = "Number of targets the connector attempted and could not examine — an unreachable "
            + "host, a refused connection, a target that answered nothing usable. Counts attempts that "
            + "yielded no item, so it never overlaps processed. Omitted when the connector does not count "
            + "failures. Reporting failures here does not degrade the run: a connector whose result is not "
            + "worth trusting reports the run itself as failed.", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Long failed;
}

package com.otilm.api.model.core.discovery;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.OffsetDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * One kind of problem a Discovery run reported, with how often it happened.
 *
 * <p>
 * <b>One row per distinct problem, not per occurrence.</b> A run against a broken estate can hit the same fault tens of
 * thousands of times, and a log that grew a line each time would bury its own first and most useful entry. Repeats
 * therefore aggregate onto the row that already carries them, advancing {@code occurrences} and {@code lastSeenAt};
 * {@code firstSeenAt} keeps pointing at when the problem started, which is usually what an operator is looking for.
 *
 * <p>
 * <b>Curated text only</b>, the same rule {@code DiscoveryErrorEvent} states for the connector side of the contract:
 * what a connector contributes to an entry here is {@code code}, from a closed vocabulary, never its own prose. The
 * constraint on {@code message} itself is on that field.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class DiscoveryMessageDto {

    /** See {@link DiscoveryMessageSeverity} for why this is the platform's to assign and not a connector's. */
    // No @Schema description on purpose: DiscoveryMessageSeverity is a schema component of its own, and OpenAPI 3.0
    // cannot carry a description beside a $ref -- swagger-core would hoist the text onto the shared component.
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    private DiscoveryMessageSeverity severity;

    /**
     * Connector-supplied for problems a Discovery Provider reported, matching {@code DiscoveryErrorEvent.code};
     * platform-assigned for the platform's own. It is the identity repeats aggregate on, so two occurrences of one
     * fault share a code even where their rendered text differs.
     */
    @Schema(description = "Identifier for the kind of problem, from a closed vocabulary — what an operator or a "
            + "support engineer matches on, and what repeated occurrences are grouped by.",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private String code;

    @Schema(description = "Human-readable description of the problem. Curated text written for a person to read; "
            + "never a raw exception message, and never text a Discovery Provider authored.",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private String message;

    // Primitive: every message exists because something happened at least once, so a box would invent an absent
    // state the contract says cannot occur.
    @Schema(description = "How many times this problem occurred during the run. At least 1.",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private long occurrences;

    @Schema(description = "When this problem was first seen during the run.",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private OffsetDateTime firstSeenAt;

    /**
     * Equal to {@code firstSeenAt} for a problem that happened once. Together the two bound the window a fault was
     * active in, which distinguishes a burst during one phase from a condition that persisted for the whole run.
     */
    @Schema(description = "When this problem was most recently seen during the run.",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private OffsetDateTime lastSeenAt;
}

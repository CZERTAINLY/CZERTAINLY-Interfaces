package com.otilm.api.model.connector.signatures.contentsigning.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.otilm.api.model.connector.common.v2.OperationStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Status of an asynchronous extension. Polling is complete once the status is terminal, at which point a successful run
 * carries the extended document and its fetch manifest, and a failed or cancelled one carries the reason.
 */
@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(name = "ExtendOperationStatusResponse", description = "Status of an asynchronous extension, with its result "
        + "once the operation has completed successfully, or the reason once it has failed or been cancelled")
public class ExtendOperationStatusResponseDto {

    @NotNull(message = "status is required")
    @Schema(description = "Where the operation stands", requiredMode = Schema.RequiredMode.REQUIRED)
    private OperationStatus status;

    @ToString.Exclude
    @Schema(description = "The extended document, base64-encoded in JSON. Present once the status is COMPLETED.",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private byte[] extendedDocument;

    @Schema(description = "Every artifact the connector fetched. Present once the status is COMPLETED.",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private List<@NotNull(
            message = "fetchManifest must not contain null items") @Valid FetchedArtifactDto> fetchManifest;

    @Schema(description = "Failure or cancellation detail when status=FAILED or CANCELLED — curated message text (no "
            + "raw exception messages)", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String reason;

    /**
     * A terminal success that carries no document leaves a poller with nothing to act on, and a result attached to a
     * non-terminal or failed status contradicts the status itself. An empty manifest stays legal on COMPLETED: it
     * records that the connector looked and found nothing to fetch.
     */
    @JsonIgnore
    @Schema(hidden = true)
    @AssertTrue(message = "extendedDocument, fetchManifest and reason must be consistent with status")
    public boolean isResultConsistentWithStatus() {
        if (status == null) {
            return true;
        }

        return switch (status) {
            case IN_PROGRESS -> extendedDocument == null && fetchManifest == null && reason == null;
            case COMPLETED ->
                extendedDocument != null && extendedDocument.length > 0 && fetchManifest != null && reason == null;
            case FAILED, CANCELLED ->
                extendedDocument == null && fetchManifest == null && reason != null && !reason.isBlank();
        };
    }
}

package com.otilm.api.model.connector.cryptography.v2.operations.data;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.otilm.api.model.connector.common.v2.OperationStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Per-item outcome of an asynchronous sign batch, correlated to its request by {@code identifier}.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Schema(name = "SignatureResultItemV2Dto", additionalProperties = Schema.AdditionalPropertiesValue.FALSE)
public class SignatureResultItemV2Dto implements IdentifiedDataV2Dto {

    @Schema(description = "Identifier of the corresponding item in the original sign request", examples = {"customId"},
            requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "identifier is required")
    private String identifier;

    @Schema(description = "Status of this item", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "status is required")
    private OperationStatus status;

    @Schema(description = "Signature over the item's data. Populated when status=COMPLETED.", format = "byte",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @ToString.Exclude
    private byte[] signature;

    @Schema(description = "Failure or cancellation detail when status=FAILED or CANCELLED — curated message text (no raw exception messages)",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String reason;

    @JsonAnySetter
    @Schema(hidden = true)
    public void rejectUnknownProperty(String property, Object ignoredValue) {
        throw new IllegalArgumentException("Unsupported v2 signature result property: " + property);
    }

    @JsonIgnore
    @Schema(hidden = true)
    @AssertTrue(message = "signature and reason must be consistent with status")
    public boolean isResultConsistentWithStatus() {
        if (status == null) {
            return true;
        }

        return switch (status) {
            case IN_PROGRESS -> signature == null && reason == null;
            case COMPLETED -> signature != null && signature.length > 0 && reason == null;
            case FAILED, CANCELLED -> signature == null && reason != null && !reason.isBlank();
        };
    }
}

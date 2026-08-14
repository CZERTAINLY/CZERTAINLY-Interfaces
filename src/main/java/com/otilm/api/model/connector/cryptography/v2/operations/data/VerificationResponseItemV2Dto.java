package com.otilm.api.model.connector.cryptography.v2.operations.data;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * One item of a V2 verification response.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VerificationResponseItemV2Dto implements IdentifiedDataV2Dto {

    @Schema(description = "Whether the signature is valid", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "result is required")
    private Boolean result;

    @Schema(description = "Identifier of the corresponding request item", examples = {"customId"},
            requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "identifier is required")
    private String identifier;

    @Schema(description = "Additional result details")
    @ToString.Exclude
    private Object details;
}

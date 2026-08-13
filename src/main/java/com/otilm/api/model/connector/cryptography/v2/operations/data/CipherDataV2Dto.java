package com.otilm.api.model.connector.cryptography.v2.operations.data;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * One item of a V2 encryption or decryption request or response.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CipherDataV2Dto implements IdentifiedDataV2Dto {

    @Schema(description = "Plaintext or a self-contained encrypted representation, depending on the operation "
            + "direction.", format = "byte", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "data is required and must not be empty")
    @ToString.Exclude
    private byte[] data;

    @Schema(description = "Identifier used to correlate the item across request and response", examples = {"customId"},
            requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "identifier is required")
    private String identifier;
}

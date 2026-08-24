package com.otilm.api.model.connector.cryptography.v2.operations;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.otilm.api.model.connector.cryptography.v2.operations.data.CipherDataV2Dto;
import com.otilm.api.model.connector.cryptography.v2.operations.validation.UniqueIdentifiers;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Response from {@code POST /v2/cryptographyProvider/operations/decrypt}. Always synchronous.
 */
@Getter
@Setter
@ToString
@Schema(name = "DecryptDataResponseV2Dto", additionalProperties = Schema.AdditionalPropertiesValue.FALSE)
public class DecryptDataResponseV2Dto {

    @Schema(description = "Decrypted data", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "decryptedData must contain at least one item")
    @UniqueIdentifiers
    private List<@NotNull(message = "decryptedData must not contain null items") @Valid CipherDataV2Dto> decryptedData;

    @JsonAnySetter
    @Schema(hidden = true)
    public void rejectUnknownProperty(String property, Object ignoredValue) {
        throw new IllegalArgumentException("Unsupported v2 decrypt response property: " + property);
    }
}

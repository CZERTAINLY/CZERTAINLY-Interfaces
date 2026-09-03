package com.otilm.api.model.connector.cryptography.v2.key;

import com.otilm.api.model.client.cryptography.key.KeyRequestType;
import com.otilm.api.model.connector.cryptography.v2.TokenProfileScopedRequestV2Dto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/** Request for the import-attribute schema of a specific key request type. */
@Getter
@Setter
@ToString(callSuper = true)
@Schema(name = "ImportKeyAttributesRequestV2Dto",
        description = "Token context and key type to read the import attribute schema for",
        additionalProperties = Schema.AdditionalPropertiesValue.FALSE)
public class ImportKeyAttributesRequestV2Dto extends TokenProfileScopedRequestV2Dto {

    @Schema(description = "Type of key to import", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "keyRequestType is required")
    private KeyRequestType keyRequestType;
}

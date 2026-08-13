package com.otilm.api.model.connector.cryptography.v2.key;

import com.otilm.api.model.client.cryptography.key.KeyRequestType;
import com.otilm.api.model.connector.cryptography.v2.TokenProfileScopedRequestV2Dto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/** Request for the creation-attribute schema of a specific key request type. */
@Getter
@Setter
@ToString(callSuper = true)
public class CreateKeyAttributesRequestV2Dto extends TokenProfileScopedRequestV2Dto {

    @Schema(description = "Type of key whose creation attributes are requested", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "keyRequestType is required")
    private KeyRequestType keyRequestType;
}

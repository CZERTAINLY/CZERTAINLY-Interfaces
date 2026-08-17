package com.otilm.api.model.connector.cryptography.v2.key;

import com.otilm.api.model.client.cryptography.key.KeyRequestType;
import com.otilm.api.model.common.attribute.common.MetadataAttribute;
import io.swagger.v3.oas.annotations.media.DiscriminatorMapping;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.List;

/**
 * OpenAPI schema for the polymorphic {@link KeyCreationResponseV2Dto} hierarchy.
 */
@Schema(name = "KeyCreationResponseInterface", description = "Key-creation response selected by key request type",
        type = "object", additionalProperties = Schema.AdditionalPropertiesValue.FALSE,
        discriminatorProperty = "keyRequestType",
        discriminatorMapping = {
                @DiscriminatorMapping(value = KeyRequestType.Codes.SECRET, schema = SecretKeyDataResponseV2Dto.class),
                @DiscriminatorMapping(value = KeyRequestType.Codes.KEY_PAIR, schema = KeyPairDataResponseV2Dto.class)},
        oneOf = {SecretKeyDataResponseV2Dto.class, KeyPairDataResponseV2Dto.class})
public interface KeyCreationResponseInterface extends Serializable {

    @Schema(description = "Type of key requested and returned", requiredMode = Schema.RequiredMode.REQUIRED)
    KeyRequestType getKeyRequestType();

    @Schema(description = "Connector-defined operation tracking metadata. Required and non-empty in the initial "
            + "response when ASYNCHRONOUS executionMode was requested in CreateKeyRequestV2Dto. Absent from the "
            + "initial response when SYNCHRONOUS executionMode was requested and from a completed result nested in "
            + "a status response.", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    List<MetadataAttribute> getOperationMeta();
}

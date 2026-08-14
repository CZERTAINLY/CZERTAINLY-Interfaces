package com.otilm.api.model.connector.cryptography.v2.key;

import com.otilm.api.model.client.cryptography.key.KeyRequestType;
import com.otilm.api.model.connector.common.v2.OperationStatus;
import io.swagger.v3.oas.annotations.media.DiscriminatorMapping;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;

/** OpenAPI schema for the polymorphic {@link KeyCreationStatusResponseV2Dto} hierarchy. */
@Schema(name = "KeyCreationStatusResponseInterface",
        description = "Status of an asynchronous key-creation operation selected by key request type", type = "object",
        discriminatorProperty = "keyRequestType",
        discriminatorMapping = {
                @DiscriminatorMapping(value = KeyRequestType.Codes.SECRET,
                        schema = SecretKeyOperationStatusResponseV2Dto.class),
                @DiscriminatorMapping(value = KeyRequestType.Codes.KEY_PAIR,
                        schema = KeyPairOperationStatusResponseV2Dto.class)},
        oneOf = {SecretKeyOperationStatusResponseV2Dto.class, KeyPairOperationStatusResponseV2Dto.class})
public interface KeyCreationStatusResponseInterface extends Serializable {

    @Schema(description = "Type of key requested by the original CreateKeyRequestV2Dto",
            requiredMode = Schema.RequiredMode.REQUIRED)
    KeyRequestType getKeyRequestType();

    @Schema(description = "Operation status as known to the connector", requiredMode = Schema.RequiredMode.REQUIRED)
    OperationStatus getStatus();

    @Schema(description = "Failure or cancellation detail when status is FAILED or CANCELLED",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    String getReason();

    @Schema(description = "Created key result. Present only when status is COMPLETED.",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    KeyCreationResponseV2Dto getResult();
}

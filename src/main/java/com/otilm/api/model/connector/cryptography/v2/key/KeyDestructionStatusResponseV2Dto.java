package com.otilm.api.model.connector.cryptography.v2.key;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/** Status response for an asynchronous key-destruction operation. */
@Getter
@Setter
@ToString(callSuper = true)
@Schema(name = "KeyDestructionStatusResponseV2Dto", additionalProperties = Schema.AdditionalPropertiesValue.FALSE)
public final class KeyDestructionStatusResponseV2Dto extends KeyOperationStatusResponseV2Dto {
}

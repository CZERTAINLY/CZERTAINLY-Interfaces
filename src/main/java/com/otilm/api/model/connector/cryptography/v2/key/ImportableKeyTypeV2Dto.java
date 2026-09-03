package com.otilm.api.model.connector.cryptography.v2.key;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.ToString;

/**
 * One key type a connector can import into a token context, with the algorithms it accepts for that type.
 */
@ToString(callSuper = true)
@Schema(name = "ImportableKeyTypeV2Dto",
        description = "Key type a connector can import, with the algorithms it accepts for that type",
        additionalProperties = Schema.AdditionalPropertiesValue.FALSE)
public class ImportableKeyTypeV2Dto extends TransferableKeyTypeV2Dto {
}

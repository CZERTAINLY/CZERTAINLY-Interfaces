package com.otilm.api.model.connector.cryptography.v2.key;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.ToString;

/**
 * One key type a connector can export from a token context, with the algorithms it accepts for that type.
 */
@ToString(callSuper = true)
@Schema(name = "ExportableKeyTypeV2Dto",
        description = "Key type a connector can export, with the algorithms it accepts for that type",
        additionalProperties = Schema.AdditionalPropertiesValue.FALSE)
public class ExportableKeyTypeV2Dto extends TransferableKeyTypeV2Dto {
}

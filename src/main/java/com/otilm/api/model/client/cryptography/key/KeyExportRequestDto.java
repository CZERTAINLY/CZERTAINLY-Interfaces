package com.otilm.api.model.client.cryptography.key;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.ToString;

/**
 * Body of a key export.
 */
@ToString(callSuper = true)
@Schema(name = "KeyExportRequestDto", description = "Passphrase and provider options for exporting a key")
public class KeyExportRequestDto extends ExportPassphraseRequestDto {
}

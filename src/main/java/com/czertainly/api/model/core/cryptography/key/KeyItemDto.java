package com.czertainly.api.model.core.cryptography.key;

import com.czertainly.api.model.client.metadata.MetadataResponseDto;
import com.czertainly.api.model.common.NameAndUuidDto;
import com.czertainly.api.model.connector.cryptography.enums.CryptographicAlgorithm;
import com.czertainly.api.model.connector.cryptography.enums.KeyFormat;
import com.czertainly.api.model.connector.cryptography.enums.KeyType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(callSuper = true)
public class KeyItemDto extends NameAndUuidDto {

    @Schema(
            description = "UUID of the key item in the Connector",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String keyReferenceUuid;

    @Schema(
            description = "Type of the Key",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private KeyType type;

    @Schema(
            description = "Key Algorithm",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private CryptographicAlgorithm cryptographicAlgorithm;

    @Schema(
            description = "Key Format"
    )
    private KeyFormat format;

    @Schema(
            description = "Key Length"
    )
    private int length;

    @Schema(
            description = "Metadata for the key"
    )
    private List<MetadataResponseDto> metadata;

    @Schema(
            description = "Key Usages",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private List<String> usage;

    @Schema(
            description = "Boolean describing if the key is enabled or not",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private boolean enabled;

    @Schema(
            description = "Key State",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private KeyState state;
}

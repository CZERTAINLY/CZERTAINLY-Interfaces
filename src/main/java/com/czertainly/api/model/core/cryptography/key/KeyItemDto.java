package com.czertainly.api.model.core.cryptography.key;

import com.czertainly.api.model.client.metadata.MetadataResponseDto;
import com.czertainly.api.model.common.NameAndUuidDto;
import com.czertainly.api.model.connector.cryptography.enums.CryptographicAlgorithm;
import com.czertainly.api.model.connector.cryptography.enums.KeyFormat;
import com.czertainly.api.model.connector.cryptography.enums.KeyType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(callSuper = true)
public class KeyItemDto extends NameAndUuidDto {

    @Schema(
            description = "UUID of the key item in the Connector",
            required = true
    )
    private String keyReferenceUuid;

    @Schema(
            description = "Type of the Key",
            required = true
    )
    private KeyType type;

    @Schema(
            description = "Key Algorithm",
            required = true
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
            required = true
    )
    private List<String> usage;

    @Schema(
            description = "Boolean describing if the key is enabled or not",
            required = true
    )
    private boolean enabled;

    @Schema(
            description = "Key State",
            required = true
    )
    private KeyState state;
}

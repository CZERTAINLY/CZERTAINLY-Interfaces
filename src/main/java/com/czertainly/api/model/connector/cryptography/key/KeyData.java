package com.czertainly.api.model.connector.cryptography.key;

import com.czertainly.api.model.common.attribute.v2.MetadataAttribute;
import com.czertainly.api.model.connector.cryptography.enums.CryptographicAlgorithm;
import com.czertainly.api.model.connector.cryptography.enums.KeyFormat;
import com.czertainly.api.model.connector.cryptography.enums.KeyType;
import com.czertainly.api.model.connector.cryptography.key.value.KeyValue;
import com.czertainly.api.model.connector.cryptography.key.value.RawKeyValue;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class KeyData {

    @Schema(
            description = "Type of the Key",
            required = true
    )
    private KeyType type;

    @Schema(
            description = "Cryptographic algorithm of the Key",
            required = true
    )
    private CryptographicAlgorithm algorithm;

    @Schema(
            description = "Format of the Key",
            required = true
    )
    private KeyFormat format;

    @Schema(
            description = "Value of the Key",
            required = true,
            anyOf = { RawKeyValue.class }
    )
    private KeyValue value;

    @Schema(
            description = "Bit length of the Key",
            required = true
    )
    private int length;

    @Schema(
            description = "Metadata for the Key, specific data that can be technology specific"
    )
    private List<MetadataAttribute> metadata;

}

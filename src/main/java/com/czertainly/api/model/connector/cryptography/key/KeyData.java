package com.czertainly.api.model.connector.cryptography.key;

import com.czertainly.api.model.common.attribute.v2.DataAttribute;
import com.czertainly.api.model.common.attribute.v2.MetadataAttribute;
import com.czertainly.api.model.common.enums.cryptography.KeyAlgorithm;
import com.czertainly.api.model.common.enums.cryptography.KeyFormat;
import com.czertainly.api.model.common.enums.cryptography.KeyType;
import com.czertainly.api.model.connector.cryptography.key.value.*;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.swagger.v3.oas.annotations.media.DiscriminatorMapping;
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
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private KeyType type;

    @Schema(
            description = "Cryptographic algorithm of the Key",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private KeyAlgorithm algorithm;

    @Schema(
            description = "Format of the Key",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private KeyFormat format;

    @Schema(
            description = "Value of the Key",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private KeyValue value;

    @Schema(
            description = "Bit length of the Key",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private int length;

    @Schema(
            description = "Metadata for the Key, specific data that can be technology specific"
    )
    private List<MetadataAttribute> metadata;

}

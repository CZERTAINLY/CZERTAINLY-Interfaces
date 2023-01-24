package com.czertainly.api.model.connector.cryptography.key;

import com.czertainly.api.model.common.attribute.v2.MetadataAttribute;
import com.czertainly.api.model.connector.cryptography.enums.CryptographicAlgorithm;
import com.czertainly.api.model.connector.cryptography.enums.KeyFormat;
import com.czertainly.api.model.connector.cryptography.enums.KeyType;
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
    private CryptographicAlgorithm algorithm;

    @Schema(
            description = "Format of the Key",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private KeyFormat format;

    @Schema(
            description = "Value of the Key",
            requiredMode = Schema.RequiredMode.REQUIRED,
            discriminatorProperty = "format",
            discriminatorMapping = {
                    @DiscriminatorMapping(value = "Raw", schema = RawKeyValue.class),
                    @DiscriminatorMapping(value = "SubjectPublicKeyInfo", schema = SpkiKeyValue.class),
                    @DiscriminatorMapping(value = "PrivateKeyInfo", schema = PrkiKeyValue.class),
                    @DiscriminatorMapping(value = "EncryptedPrivateKeyInfo", schema = EprkiKeyValue.class),
                    @DiscriminatorMapping(value = "Custom", schema = CustomKeyValue.class)
            },
            oneOf = {
                    RawKeyValue.class,
                    SpkiKeyValue.class,
                    PrkiKeyValue.class,
                    EprkiKeyValue.class,
                    CustomKeyValue.class
            }
    )
    @JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXTERNAL_PROPERTY, property = "format")
    @JsonSubTypes({
            @JsonSubTypes.Type(value = RawKeyValue.class, name = "Raw"),
            @JsonSubTypes.Type(value = SpkiKeyValue.class, name = "SubjectPublicKeyInfo"),
            @JsonSubTypes.Type(value = PrkiKeyValue.class, name = "PrivateKeyInfo"),
            @JsonSubTypes.Type(value = EprkiKeyValue.class, name = "EncryptedPrivateKeyInfo"),
            @JsonSubTypes.Type(value = CustomKeyValue.class, name = "Custom")
    })
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

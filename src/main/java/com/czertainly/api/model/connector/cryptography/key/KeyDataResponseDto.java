package com.czertainly.api.model.connector.cryptography.key;

import com.czertainly.api.model.common.attribute.v2.MetadataAttribute;
import com.czertainly.api.model.connector.cryptography.enums.CryptographicAlgorithm;
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
public class KeyDataResponseDto {

    @Schema(
            description = "Attributes for the Key"
    )
    private List<MetadataAttribute> keyAttributes;

    @Schema(description = "Cryptographic algorithm of the Key",
            required = true
    )
    private CryptographicAlgorithm cryptographicAlgorithm;

}

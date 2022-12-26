package com.czertainly.api.model.core.cryptography.key;

import com.czertainly.api.model.client.attribute.ResponseAttributeDto;
import com.czertainly.api.model.common.NameAndUuidDto;
import com.czertainly.api.model.common.attribute.v2.MetadataAttribute;
import com.czertainly.api.model.connector.cryptography.enums.CryptographicAlgorithm;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class KeyDetailDto extends NameAndUuidDto {

    @Schema(
            description = "Attributes for the Key"
    )
    private List<ResponseAttributeDto> keyAttributes;

    @Schema(description = "Cryptographic algorithm of the Key",
            required = true
    )
    private CryptographicAlgorithm cryptographicAlgorithm;

    @Schema(
            description = "Custom Attributes for the Token Instance"
    )
    private List<ResponseAttributeDto> customAttributes;
}

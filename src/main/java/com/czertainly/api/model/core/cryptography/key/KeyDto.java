package com.czertainly.api.model.core.cryptography.key;

import com.czertainly.api.model.common.NameAndUuidDto;
import com.czertainly.api.model.connector.cryptography.enums.CryptographicAlgorithm;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class KeyDto extends NameAndUuidDto {

    @Schema(description = "Description of the Key",
            required = true
    )
    private String description;

    @Schema(description = "Cryptographic algorithm of the Key",
            required = true
    )
    private CryptographicAlgorithm cryptographicAlgorithm;

    @Schema(
            description = "UUID of the Token Profile"
    )
    private String tokenProfileUuid;

    @Schema(
            description = "Name of the Token Profile"
    )
    private String tokenProfileName;

    @Schema(
            description = "Token Instance UUID",
            required = true
    )
    private String tokenInstanceUuid;

    @Schema(
            description = "Token Instance Name",
            required = true
    )
    private String tokenInstanceName;

}

package com.czertainly.api.model.client.cryptography.key;

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
@ToString
public class EditKeyRequestDto {

    @Schema(
            description = "UUID of the token profile",
            required = true
    )
    private String tokenProfileUuid;

    @Schema(
            description = "Name of the Cryptographic Key",
            required = true
    )
    private String name;

    @Schema(
            description = "Description of the Cryptographic Key",
            required = true
    )
    private String description;

    @Schema(
            description = "Key Owner"
    )
    private String owner;

    @Schema(
            description = "UUID of the group"
    )
    private String groupUuid;

}

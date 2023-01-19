package com.czertainly.api.model.client.cryptography.key;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class EditKeyRequestDto {

    @Schema(
            description = "UUID of the token profile",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String tokenProfileUuid;

    @Schema(
            description = "Name of the Cryptographic Key",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String name;

    @Schema(
            description = "Description of the Cryptographic Key",
            requiredMode = Schema.RequiredMode.REQUIRED
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

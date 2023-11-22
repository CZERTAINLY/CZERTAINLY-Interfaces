package com.czertainly.api.model.client.cryptography.key;

import com.czertainly.api.model.client.attribute.RequestAttributeDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

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
            description = "Key Owner UUID"
    )
    private String ownerUuid;

    @Schema(
            description = "UUID of the group"
    )
    private String groupUuid;

    @Schema(
            description = "List of Custom Attributes"
    )
    private List<RequestAttributeDto> customAttributes;
}

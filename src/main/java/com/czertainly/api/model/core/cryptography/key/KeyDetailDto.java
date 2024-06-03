package com.czertainly.api.model.core.cryptography.key;

import com.czertainly.api.model.client.attribute.ResponseAttributeDto;
import com.czertainly.api.model.common.NameAndUuidDto;
import com.czertainly.api.model.core.certificate.group.GroupDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.OffsetDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class KeyDetailDto extends NameAndUuidDto {

    @Schema(description = "Description of the Key",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String description;

    @Schema(description = "Creation time of the Key. If the key is discovered from the connector, then it will be returned",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private OffsetDateTime creationTime;

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
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String tokenInstanceUuid;

    @Schema(
            description = "Token Instance Name",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String tokenInstanceName;

    @Schema(
            description = "Custom Attributes for the Cryptographic Key"
    )
    private List<ResponseAttributeDto> customAttributes;

    @Schema(
            description = "Attributes for the Cryptographic Key",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private List<ResponseAttributeDto> attributes;

    @Schema(
            description = "Owner of the Key"
    )
    private String owner;

    @Schema(
            description = "UUID of the owner of the Key"
    )
    private String ownerUuid;

    @Schema(
            description = "Groups associated to the key"
    )
    private List<GroupDto> groups;

    @Schema(
            description = "Key Objects",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private List<KeyItemDetailDto> items;

    @Schema(
            description = "List of associated items"
    )
    private List<KeyAssociationDto> associations;
}

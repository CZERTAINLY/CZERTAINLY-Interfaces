package com.czertainly.api.model.core.cryptography.key;

import com.czertainly.api.model.common.NameAndUuidDto;
import com.czertainly.api.model.core.certificate.group.GroupDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.OffsetDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class KeyDto extends NameAndUuidDto {

    @Schema(description = "Description of the Key",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    private String description;

    @Schema(description = "Creation time of the Key. If the key is discovered from the connector, then it will be returned",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private OffsetDateTime creationTime;

    @Schema(
            description = "UUID of the Token Profile",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    private String tokenProfileUuid;

    @Schema(
            description = "Name of the Token Profile",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    private String tokenProfileName;

    @Schema(
            description = "Token Instance UUID",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    private String tokenInstanceUuid;

    @Schema(
            description = "Token Instance Name",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    private String tokenInstanceName;

    @Schema(
            description = "Owner of the Key",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    private String owner;

    @Schema(
            description = "UUID of the owner of the Key",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    private String ownerUuid;

    @Schema(
            description = "Groups associated to the key",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    private List<GroupDto> groups;

    @Schema(
            description = "Key Items",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private List<KeyItemDto> items;

    @Schema(
            description = "Number of associated objects",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private int associations;

}

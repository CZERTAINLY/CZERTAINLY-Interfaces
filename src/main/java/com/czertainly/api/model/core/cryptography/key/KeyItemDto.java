package com.czertainly.api.model.core.cryptography.key;

import com.czertainly.api.model.common.NameAndUuidDto;
import com.czertainly.api.model.common.enums.cryptography.KeyAlgorithm;
import com.czertainly.api.model.common.enums.cryptography.KeyFormat;
import com.czertainly.api.model.common.enums.cryptography.KeyType;
import com.czertainly.api.model.core.certificate.group.GroupDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(callSuper = true)
public class KeyItemDto extends NameAndUuidDto {

    @Schema(description = "Description of the Key",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String description;

    @Schema(description = "Creation time of the Key. If the key is discovered from the connector, then it will be returned",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private LocalDateTime creationTime;

    @Schema(description = "UUID of the wrapper object",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String keyWrapperUuid;

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
            description = "Owner of the Key"
    )
    private String owner;

    @Schema(
            description = "UUID of the owner of the Key"
    )
    private String ownerUuid;

    @Schema(
            description = "Groups associated to the Key"
    )
    private List<GroupDto> groups;

    @Schema(
            description = "Number of associated objects"
    )
    private int associations;

    @Schema(
            description = "UUID of the key item in the Connector",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String keyReferenceUuid;

    @Schema(
            description = "Type of the Key",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private KeyType type;

    @Schema(
            description = "Key Algorithm",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private KeyAlgorithm keyAlgorithm;

    @Schema(
            description = "Key Format"
    )
    private KeyFormat format;

    @Schema(
            description = "Key Length"
    )
    private int length;
    @Schema(
            description = "Key Usages",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private List<KeyUsage> usage;

    @Schema(
            description = "Boolean describing if the key is enabled or not",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private boolean enabled;

    @Schema(
            description = "Key State",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private KeyState state;
}

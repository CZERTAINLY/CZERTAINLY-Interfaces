package com.czertainly.api.model.core.cryptography.tokenprofile;

import com.czertainly.api.model.common.NameAndUuidDto;
import com.czertainly.api.model.connector.cryptography.enums.TokenInstanceStatus;
import com.czertainly.api.model.core.cryptography.key.KeyUsage;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class TokenProfileDto extends NameAndUuidDto {
    @Schema(
            description = "Description of Token Profile",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    private String description;

    @Schema(
            description = "UUID of Token Instance",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String tokenInstanceUuid;

    @Schema(
            description = "Name of Token instance",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String tokenInstanceName;

    @Schema(
            description = "Token Instance Status",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private TokenInstanceStatus tokenInstanceStatus;

    @Schema(
            description = "Enabled flag - true = enabled; false = disabled",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private Boolean enabled;

    @Schema(
            description = "Usages for the Keys assoiated to the profile",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private List<KeyUsage> usages;
}

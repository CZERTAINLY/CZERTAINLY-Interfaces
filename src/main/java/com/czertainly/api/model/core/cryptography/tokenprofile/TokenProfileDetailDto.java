package com.czertainly.api.model.core.cryptography.tokenprofile;

import com.czertainly.api.model.client.attribute.ResponseAttributeDto;
import com.czertainly.api.model.common.NameAndUuidDto;
import com.czertainly.api.model.connector.cryptography.enums.TokenInstanceStatus;
import com.czertainly.api.model.core.cryptography.key.KeyUsage;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class TokenProfileDetailDto extends NameAndUuidDto {
    @Schema(
            description = "Description of Token Profile"
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
            description = "List of Token Profile attributes",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private List<ResponseAttributeDto<?>> attributes = new ArrayList<>();

    @Schema(
            description = "List of Custom Attributes"
    )
    private List<ResponseAttributeDto<?>> customAttributes;

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

package com.czertainly.api.model.core.cryptography.tokenprofile;

import com.czertainly.api.model.client.attribute.ResponseAttributeDto;
import com.czertainly.api.model.common.NameAndUuidDto;
import com.czertainly.api.model.connector.cryptography.enums.TokenInstanceStatus;
import com.czertainly.api.model.core.cryptography.key.KeyUsage;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

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
            required = true
    )
    private String tokenInstanceUuid;

    @Schema(
            description = "Name of Token instance",
            required = true
    )
    private String tokenInstanceName;

    @Schema(
            description = "List of Token Profile attributes",
            required = true
    )
    private List<ResponseAttributeDto> attributes = new ArrayList<>();

    @Schema(
            description = "List of Custom Attributes"
    )
    private List<ResponseAttributeDto> customAttributes;

    @Schema(
            description = "Token Instance Status",
            required = true
    )
    private TokenInstanceStatus tokenInstanceStatus;

    @Schema(
            description = "Enabled flag - true = enabled; false = disabled",
            required = true
    )
    private Boolean enabled;

    @Schema(
            description = "Usages for the Keys assoiated to the profile",
            required = true
    )
    private List<KeyUsage> usages;
}

package com.czertainly.api.model.core.cryptography.token;

import com.czertainly.api.model.client.attribute.ResponseAttributeDto;
import com.czertainly.api.model.client.metadata.MetadataResponseDto;
import com.czertainly.api.model.common.NameAndUuidDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(callSuper = true)
public class TokenInstanceDetailDto extends NameAndUuidDto {

    @Schema(
            description = "Connector Name"
    )
    private String connectorName;

    @Schema(
            description = "Connector UUID"
    )
    private String connectorUuid;

    @Schema(
            description = "Connector Kind"
    )
    private String kind;

    @Schema(
            description = "Status Of the Token Instance",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private TokenInstanceStatusDetailDto status;

    @Schema(
            description = "Number of Token Profiles associated",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private Integer tokenProfiles;

    @Schema(
            description = "List of Token instance Attributes",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private List<ResponseAttributeDto> attributes;

    @Schema(
            description = "Token instance Metadata"
    )
    private List<MetadataResponseDto> metadata;

    @Schema(
            description = "Custom Attributes for the Token Instance"
    )
    private List<ResponseAttributeDto> customAttributes;
}


package com.czertainly.api.model.core.cryptography.token;

import com.czertainly.api.model.client.attribute.ResponseAttributeDto;
import com.czertainly.api.model.client.metadata.MetadataResponseDto;
import com.czertainly.api.model.common.NameAndUuidDto;
import com.czertainly.api.model.connector.cryptography.enums.TokenInstanceStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

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
            description = "Status Of the Token Instance",
            required = true
    )
    private TokenInstanceStatus status;

    @Schema(
            description = "Number of Token Profiles associated",
            required = true
    )
    private Integer tokenProfiles;

    @Schema(
            description = "List of Token instance Attributes",
            required = true
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


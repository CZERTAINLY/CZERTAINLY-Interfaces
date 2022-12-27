package com.czertainly.api.model.client.cryptography.token;

import com.czertainly.api.model.client.attribute.RequestAttributeDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TokenInstanceRequestDto {
    @Schema(
            description = "Name of the Token Instance",
            required = true
    )
    private String name;

    @Schema(
            description = "Token Instance description"
    )
    private String description;

    @Schema(
            description = "UUID of the Connector",
            required = true
    )
    private String connectorUuid;

    @Schema(
            description = "Connector Kind",
            required = true
    )
    private String kind;

    @Schema(
            description = "Custom Attributes",
            required = true
    )
    private List<RequestAttributeDto> customAttributes;

    @Schema(
            description = "Attributes for Token Instance",
            required = true
    )
    private List<RequestAttributeDto> attributes;
}

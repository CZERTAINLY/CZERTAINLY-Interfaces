package com.czertainly.api.model.client.cryptography.key;

import com.czertainly.api.model.client.attribute.RequestAttributeDto;
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
@ToString
public class KeyRequestDto {

    @Schema(
            description = "Name of the Cryptographic Key",
            required = true
    )
    private String name;

    @Schema(
            description = "Description of the Cryptographic Key",
            required = true
    )
    private String description;

    @Schema(
            description = "List of Attributes to create a Key",
            required = true
    )
    private List<RequestAttributeDto> attributes;

    @Schema(
            description = "Custom Attributes for the key"
    )
    private List<RequestAttributeDto> customAttributes;

}
